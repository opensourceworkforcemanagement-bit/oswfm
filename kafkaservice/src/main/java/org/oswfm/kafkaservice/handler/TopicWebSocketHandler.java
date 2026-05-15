package org.oswfm.kafkaservice.handler;

import org.oswfm.kafkaservice.service.KafkaBridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket handler that streams Kafka topic messages to subscribed clients.
 *
 * <p>Clients connect as:
 * {@code ws://<host>:1120/ws/topics/<topicName>?clientId=<id>}
 */
@Slf4j
@Component
public class TopicWebSocketHandler extends TextWebSocketHandler {

    /** topicName → set of active WebSocket sessions subscribed to that topic */
    private final Map<String, Set<WebSocketSession>> topicSessions = new ConcurrentHashMap<>();

    /** sessionId → topicName (for efficient cleanup on disconnect) */
    private final Map<String, String> sessionTopicMap = new ConcurrentHashMap<>();

    private final KafkaBridgeService kafkaBridgeService;

    // @Lazy breaks the circular dependency: WebSocketConfig → TopicWebSocketHandler → KafkaBridgeService → TopicWebSocketHandler
    public TopicWebSocketHandler(@Lazy KafkaBridgeService kafkaBridgeService) {
        this.kafkaBridgeService = kafkaBridgeService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String topicName = extractTopicName(session);
        if (topicName == null) {
            log.warn("[Kafka-WS] Could not extract topic name from URI: {}", session.getUri());
            closeQuietly(session);
            return;
        }

        String clientId = extractParam(session, "clientId");
        topicSessions.computeIfAbsent(topicName, k -> new CopyOnWriteArraySet<>()).add(session);
        sessionTopicMap.put(session.getId(), topicName);

        log.info("[Kafka-WS] Client connected: sessionId={} topic={} clientId={}", session.getId(), topicName, clientId);

        kafkaBridgeService.ensureConsumerActive(topicName);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String topicName = sessionTopicMap.remove(session.getId());
        if (topicName != null) {
            Set<WebSocketSession> sessions = topicSessions.get(topicName);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    topicSessions.remove(topicName);
                    kafkaBridgeService.releaseConsumerIfIdle(topicName);
                }
            }
        }
        log.info("[Kafka-WS] Client disconnected: sessionId={} topic={} status={}", session.getId(), topicName, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("[Kafka-WS] Transport error: sessionId={}", session.getId(), exception);
        afterConnectionClosed(session, CloseStatus.SERVER_ERROR);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.debug("[Kafka-WS] Ignoring inbound text from sessionId={} (use REST /publish)", session.getId());
    }

    public void push(String topicName, String eventJson) {
        Set<WebSocketSession> sessions = topicSessions.get(topicName);
        if (sessions == null || sessions.isEmpty()) {
            log.debug("[Kafka-WS] No subscribers for topic={}, dropping message", topicName);
            return;
        }

        TextMessage msg = new TextMessage(eventJson);
        int sent = 0;
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                try {
                    synchronized (s) {
                        s.sendMessage(msg);
                    }
                    sent++;
                } catch (IOException e) {
                    log.error("[Kafka-WS] Failed to send to sessionId={}: {}", s.getId(), e.getMessage());
                }
            }
        }
        log.debug("[Kafka-WS] Pushed to {}/{} session(s) for topic={}", sent, sessions.size(), topicName);
    }

    public int subscriberCount(String topicName) {
        Set<WebSocketSession> sessions = topicSessions.get(topicName);
        return sessions == null ? 0 : sessions.size();
    }

    private String extractTopicName(WebSocketSession session) {
        if (session.getUri() == null) return null;
        String path = session.getUri().getPath();
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash < 0 || lastSlash == path.length() - 1) return null;
        String name = path.substring(lastSlash + 1);
        return name.isBlank() ? null : name;
    }

    private String extractParam(WebSocketSession session, String name) {
        if (session.getUri() == null) return null;
        String query = session.getUri().getQuery();
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2 && kv[0].equals(name)) return kv[1];
        }
        return null;
    }

    private void closeQuietly(WebSocketSession session) {
        try {
            session.close(CloseStatus.BAD_DATA);
        } catch (IOException ignored) {
        }
    }
}

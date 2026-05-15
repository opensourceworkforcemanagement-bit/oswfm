package org.oswfm.signalingservice.handler;

import org.oswfm.signalingservice.model.SignalMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * WebSocket handler that manages WebRTC signaling.
 *
 * <p>Rooms are keyed by {@code roomId}. Each peer joining a room broadcasts
 * a {@code join} message to existing members; subsequent offer/answer/candidate
 * messages are either unicast (when {@code to} is set) or broadcast to the
 * rest of the room.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    /** roomId → set of active sessions in that room. */
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    /** sessionId → roomId (for cleanup on disconnect). */
    private final Map<String, String> sessionRoom = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket connected: sessionId={}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        String roomId = sessionRoom.remove(sessionId);

        if (roomId != null) {
            Set<WebSocketSession> room = rooms.get(roomId);
            if (room != null) {
                room.remove(session);
                if (room.isEmpty()) {
                    rooms.remove(roomId);
                    log.info("Room '{}' removed (last peer left)", roomId);
                } else {
                    broadcastLeave(roomId, sessionId, room);
                }
            }
        }

        log.info("WebSocket disconnected: sessionId={}, status={}", sessionId, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket transport error: sessionId={}", session.getId(), exception);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage rawMessage) throws Exception {
        SignalMessage msg;
        try {
            msg = objectMapper.readValue(rawMessage.getPayload(), SignalMessage.class);
        } catch (Exception e) {
            log.warn("Failed to parse signal message from {}: {}", session.getId(), e.getMessage());
            return;
        }

        msg.setFrom(session.getId());
        String roomId = resolveRoomId(msg, session);

        switch (msg.getType()) {
            case "join"      -> handleJoin(session, msg, roomId);
            case "offer",
                 "answer",
                 "candidate" -> handleRelay(session, msg, roomId);
            case "leave"     -> handleLeave(session, roomId);
            default          -> log.warn("Unknown signal type '{}' from {}", msg.getType(), session.getId());
        }
    }

    private void handleJoin(WebSocketSession session, SignalMessage msg, String roomId) throws IOException {
        rooms.computeIfAbsent(roomId, k -> new CopyOnWriteArraySet<>());
        Set<WebSocketSession> room = rooms.get(roomId);

        broadcastToRoom(roomId, msg, session, room);

        room.add(session);
        sessionRoom.put(session.getId(), roomId);

        log.info("Peer {} joined room '{}' ({} peer(s) now)", session.getId(), roomId, room.size());
    }

    private void handleRelay(WebSocketSession session, SignalMessage msg, String roomId) throws IOException {
        Set<WebSocketSession> room = rooms.get(roomId);
        if (room == null) {
            log.warn("Relay attempted for unknown room '{}' from {}", roomId, session.getId());
            return;
        }

        String target = msg.getTo();
        if (target != null && !target.isBlank()) {
            room.stream()
                    .filter(s -> s.getId().equals(target) && s.isOpen())
                    .findFirst()
                    .ifPresentOrElse(
                            s -> sendQuietly(s, msg),
                            () -> log.warn("Target peer '{}' not found in room '{}'", target, roomId)
                    );
        } else {
            broadcastToRoom(roomId, msg, session, room);
        }
    }

    private void handleLeave(WebSocketSession session, String roomId) {
        Set<WebSocketSession> room = rooms.get(roomId);
        if (room != null) {
            room.remove(session);
            sessionRoom.remove(session.getId());
            broadcastLeave(roomId, session.getId(), room);
            if (room.isEmpty()) rooms.remove(roomId);
        }
    }

    private String resolveRoomId(SignalMessage msg, WebSocketSession session) {
        if (msg.getRoomId() != null && !msg.getRoomId().isBlank()) {
            return msg.getRoomId();
        }
        String path = session.getUri() != null ? session.getUri().getPath() : "";
        String[] segments = path.split("/");
        if (segments.length >= 3) {
            return segments[segments.length - 1];
        }
        return "default";
    }

    private void broadcastToRoom(String roomId, SignalMessage msg, WebSocketSession sender,
                                  Set<WebSocketSession> room) {
        room.stream()
                .filter(s -> !s.getId().equals(sender.getId()) && s.isOpen())
                .forEach(s -> sendQuietly(s, msg));
    }

    private void broadcastLeave(String roomId, String leavingSessionId, Set<WebSocketSession> room) {
        SignalMessage leaveMsg = new SignalMessage();
        leaveMsg.setType("leave");
        leaveMsg.setRoomId(roomId);
        leaveMsg.setFrom(leavingSessionId);

        room.stream()
                .filter(WebSocketSession::isOpen)
                .forEach(s -> sendQuietly(s, leaveMsg));
    }

    private void sendQuietly(WebSocketSession session, SignalMessage msg) {
        try {
            String payload = objectMapper.writeValueAsString(msg);
            synchronized (session) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(payload));
                }
            }
        } catch (IOException e) {
            log.error("Failed to send message to session {}: {}", session.getId(), e.getMessage());
        }
    }
}

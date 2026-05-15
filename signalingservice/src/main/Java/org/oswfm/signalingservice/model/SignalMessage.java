package org.oswfm.signalingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic signaling message envelope exchanged over WebSocket.
 *
 * <p>Type values:</p>
 * <ul>
 *   <li>{@code join}      – peer announces itself in a room</li>
 *   <li>{@code offer}     – SDP offer from the caller</li>
 *   <li>{@code answer}    – SDP answer from the callee</li>
 *   <li>{@code candidate} – ICE candidate from either peer</li>
 *   <li>{@code leave}     – peer is disconnecting</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalMessage {

    /** Message type: join | offer | answer | candidate | leave */
    private String type;

    /** Room identifier used to scope signaling between peers. */
    private String roomId;

    /** The sender's session ID (set by the server, ignored if sent by client). */
    private String from;

    /** Target peer session ID. When null the message is broadcast to all room members. */
    private String to;

    /** SDP payload – present for offer and answer types. */
    private JsonNode sdp;

    /** ICE candidate payload – present for candidate type. */
    private JsonNode candidate;
}

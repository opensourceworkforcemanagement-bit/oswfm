package org.oswfm.commons.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Notification-specific REST request body extending the generic {@link RestMessageRequest}.
 *
 * Adds targeting, priority, and TTL on top of the base envelope.
 *
 * Example:
 * {
 *   "id": "evt-001",
 *   "type": "ALERT",
 *   "sequence": 1,
 *   "timestamp": 1741651200000,
 *   "source": "notificationservice",
 *   "priority": "HIGH",
 *   "ttl": 60000,
 *   "requestingUserId": "user-789",
 *   "targetUserIds": ["user-123", "user-456"],
 *   "payload": {
 *     "title": "Hello",
 *     "body": "Test notification",
 *     "clickAction": "/incidents/42",
 *     "imageUrl": "https://example.com/img.png",
 *     "data": {}
 *   }
 * }
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationRequest extends RestMessageRequest {

    /** The user ID of the originating requester. */
    private String requestingUserId;

    /**
     * When set, the notification is delivered only to these user IDs.
     * Null or empty means broadcast to all connected sessions.
     */
    private List<String> targetUserIds;
}

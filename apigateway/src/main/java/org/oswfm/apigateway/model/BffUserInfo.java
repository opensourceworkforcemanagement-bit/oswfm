package org.oswfm.apigateway.model;

/**
 * User identity returned to the SPA after a successful BFF login or {@code /me} call.
 *
 * <p>This is the only auth-related data the browser ever receives — no tokens are included.
 * The SPA uses this to display user information and drive UI state (navigation, greetings, etc.).
 */
public record BffUserInfo(
        String userId,
        String username,
        String firstName,
        String lastName,
        String userType
) {}

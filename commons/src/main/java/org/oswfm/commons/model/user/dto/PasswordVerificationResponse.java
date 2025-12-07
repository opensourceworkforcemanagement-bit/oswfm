package org.oswfm.commons.model.user.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for password verification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerificationResponse {
    
    private boolean valid;
    private String message;
    private boolean accountLocked;
    private boolean passwordExpired;
    private boolean mustChangePassword;
    private OffsetDateTime lockedUntil;
    private int failedAttempts;
    private int maxFailedAttempts;
}

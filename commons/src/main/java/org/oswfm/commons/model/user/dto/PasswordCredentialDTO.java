package org.oswfm.commons.model.user.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordCredentialDTO {
    
    private Integer  credentialId;
    private Integer  userId;
    private String username;
    private String algorithm;
    private Integer  failedAttempts;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime lastChangedAt;
    private OffsetDateTime expiresAt;
    private Boolean mustChangePassword;
    private Boolean isLocked;
    private Boolean isPasswordExpired;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
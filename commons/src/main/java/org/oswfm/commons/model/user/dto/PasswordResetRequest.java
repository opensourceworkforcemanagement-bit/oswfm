package org.oswfm.commons.model.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for password reset flow
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    
    @NotBlank(message = "User ID or username is required")
    private String userIdentifier; // userId or username
    
    @NotBlank(message = "Reset token is required")
    private String resetToken;
    
    @NotBlank(message = "New password is required")
    @Size(min = 12, max = 128, message = "Password must be between 12 and 128 characters")
    private String newPassword;
    
    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}

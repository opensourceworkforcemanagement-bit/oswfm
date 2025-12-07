package org.oswfm.commons.model.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for password verification (login)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerificationRequest {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}

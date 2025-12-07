package org.oswfm.commons.model.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordRequest {
    
    @NotBlank(message = "User ID is required")
    private Integer  userId;
    
    @NotBlank(message = "Password is required")
    @Size(min = 12, max = 128, message = "Password must be between 12 and 128 characters")
    private String password;
    
    private Boolean mustChangePassword = false;
    
    private Integer  expirationDays;
}

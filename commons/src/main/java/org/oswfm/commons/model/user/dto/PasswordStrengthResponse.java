package org.oswfm.commons.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordStrengthResponse {
    
    private boolean valid;
    private int strength; // 0-100
    private String strengthLevel; // WEAK, FAIR, GOOD, STRONG, VERY_STRONG
    private String message;
    private java.util.List<String> violations;
    private java.util.List<String> suggestions;
}

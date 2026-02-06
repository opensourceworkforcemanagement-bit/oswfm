package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {
    
    private  Integer actionId;
    
    @NotBlank(message = "Action name is required")
    private String actionName;
    
    private String description;
    private OffsetDateTime createdAt;
}

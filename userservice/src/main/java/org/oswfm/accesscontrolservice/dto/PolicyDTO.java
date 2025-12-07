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
public class PolicyDTO {
    
    private  Integer policyId;
    
    @NotBlank(message = "Policy name is required")
    private String policyName;
    
    private String description;
    
    @NotBlank(message = "Policy type is required")
    private String policyType;
    
    private Integer  priority;
    private Boolean isActive;
    private  Integer createdById;
    private String createdByUsername;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

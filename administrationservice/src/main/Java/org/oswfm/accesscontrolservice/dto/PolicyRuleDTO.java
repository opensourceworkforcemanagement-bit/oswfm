package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRuleDTO {
    
    private  Integer ruleId;
    
    @NotNull(message = "Policy ID is required")
    private  Integer policyId;
    
    private String ruleName;
    
    @NotNull(message = "Attribute ID is required")
    private  Integer attributeId;
    
    private String attributeName;
    
    @NotBlank(message = "Operator is required")
    private String operator;
    
    @NotBlank(message = "Comparison value is required")
    private String comparisonValue;
    
    private String logicalOperator = "AND";
    
    private Integer  ruleOrder = 0;
    
    private OffsetDateTime createdAt;
}

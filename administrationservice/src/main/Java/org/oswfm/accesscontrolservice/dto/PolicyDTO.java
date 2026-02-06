package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    private Integer policyId;

    @NotBlank(message = "Policy name is required")
    private String policyName;

    private String description;

    @NotNull(message = "Policy type ID is required")
    private Integer policyTypeId;
    private String policyTypeName;

    private Integer priority;
    private Boolean isActive;
    private Integer createdById;
    private String createdByUsername;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

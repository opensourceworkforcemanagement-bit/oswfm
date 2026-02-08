package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyObligationDTO {

    private Integer obligationId;

    @NotNull(message = "Policy ID is required")
    private Integer policyId;

    @NotNull(message = "Obligation Type ID is required")
    private Integer obligationTypeId;

    private String obligationTypeName;

    private String obligationParams;

    private Boolean isMandatory = true;

    private OffsetDateTime createdAt;
}

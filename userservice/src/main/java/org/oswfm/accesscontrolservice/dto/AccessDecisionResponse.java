package org.oswfm.accesscontrolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessDecisionResponse {
    
    public enum Decision {
        PERMIT,
        DENY,
        NOT_APPLICABLE,
        INDETERMINATE
    }
    
    private Decision decision;
    private String reason;
    private  Integer appliedPolicyId;
    private String appliedPolicyName;
    private OffsetDateTime timestamp;
    private List<String> evaluationDetails = new ArrayList<>();
    
    public AccessDecisionResponse(Decision decision, String reason) {
        this.decision = decision;
        this.reason = reason;
        this.timestamp = OffsetDateTime.now();
    }
    
    public void addEvaluationDetail(String detail) {
        this.evaluationDetails.add(detail);
    }
}

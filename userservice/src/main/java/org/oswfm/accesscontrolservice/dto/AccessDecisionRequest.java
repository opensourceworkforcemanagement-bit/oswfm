package org.oswfm.accesscontrolservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessDecisionRequest {
    
    private  Integer userId;
    private  Integer resourceId;
    private  Integer actionId;
    
    // Optional: additional context attributes
    private Map<String, String> environmentAttributes = new HashMap<>();
    
    // For logging purposes
    private String sourceIp;
    private String userAgent;
    private String sessionId;
}

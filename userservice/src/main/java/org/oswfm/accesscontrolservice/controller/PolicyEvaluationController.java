package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.AccessDecisionRequest;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse;
import org.oswfm.accesscontrolservice.service.PolicyEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access")
@RequiredArgsConstructor
@Tag(name = "Access Control", description = "Policy evaluation and access decision APIs")
public class PolicyEvaluationController {
    
    private final PolicyEvaluationService policyEvaluationService;
    
    @PostMapping("/evaluate")
    @Operation(summary = "Evaluate access decision", 
               description = "Evaluates whether a user can perform an action on a resource based on ABAC policies")
    public ResponseEntity<AccessDecisionResponse> evaluateAccess(
            @RequestBody AccessDecisionRequest request,
            HttpServletRequest httpRequest) {
        
        // Automatically populate request metadata
        if (request.getSourceIp() == null) {
            request.setSourceIp(httpRequest.getRemoteAddr());
        }
        if (request.getUserAgent() == null) {
            request.setUserAgent(httpRequest.getHeader("User-Agent"));
        }
        if (request.getSessionId() == null && httpRequest.getSession(false) != null) {
            request.setSessionId(httpRequest.getSession().getId());
        }
        
        AccessDecisionResponse response = policyEvaluationService.evaluateAccess(request);
        
        return ResponseEntity.ok(response);
    }
}

package org.oswfm.accesscontrolservice.controller;

import java.util.List;

import org.oswfm.accesscontrolservice.dto.AccessDecisionRequest;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse;
import org.oswfm.accesscontrolservice.dto.PolicyDTO;
import org.oswfm.accesscontrolservice.service.PolicyEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/access")
@RequiredArgsConstructor
@Tag(name = "Access Control", description = "Policy evaluation and access decision APIs")
public class PolicyEvaluationController {
    
    @Autowired
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

    @GetMapping("/policies/user/{userId}")
    @Operation(
        summary = "Get relevant policies for a user",
        description = "Returns active policies that would be evaluated for the given user " +
                      "based on their resolved subject attributes. Useful for auditing."
    )
    public ResponseEntity<List<PolicyDTO>> getPoliciesForUser(@PathVariable Integer userId) {
        List<PolicyDTO> policies = policyEvaluationService.getPoliciesForUser(userId);
        return ResponseEntity.ok(policies);
    }
}

package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.service.PolicyTargetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
@Tag(name = "Policy Targets", description = "Manage policy action and resource targets")
public class PolicyTargetController {
    
    private final PolicyTargetService policyTargetService;
    
    @PostMapping("/{policyId}/actions/{actionId}")
    @Operation(summary = "Add action target to policy")
    public ResponseEntity<Void> addActionToPolicy(@PathVariable  Integer policyId, 
                                                   @PathVariable  Integer actionId) {
        policyTargetService.addActionToPolicy(policyId, actionId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{policyId}/actions/{actionId}")
    @Operation(summary = "Remove action target from policy")
    public ResponseEntity<Void> removeActionFromPolicy(@PathVariable  Integer policyId, 
                                                        @PathVariable  Integer actionId) {
        policyTargetService.removeActionFromPolicy(policyId, actionId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{policyId}/resources/{resourceId}")
    @Operation(summary = "Add resource target to policy")
    public ResponseEntity<Void> addResourceToPolicy(@PathVariable  Integer policyId, 
                                                     @PathVariable  Integer resourceId) {
        policyTargetService.addResourceToPolicy(policyId, resourceId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{policyId}/resource-types/{resourceType}")
    @Operation(summary = "Add resource type target to policy")
    public ResponseEntity<Void> addResourceTypeToPolicy(@PathVariable  Integer policyId, 
                                                         @PathVariable String resourceType) {
        policyTargetService.addResourceTypeToPolicy(policyId, resourceType);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{policyId}/resources/{resourceId}")
    @Operation(summary = "Remove resource target from policy")
    public ResponseEntity<Void> removeResourceFromPolicy(@PathVariable  Integer policyId, 
                                                          @PathVariable  Integer resourceId) {
        policyTargetService.removeResourceFromPolicy(policyId, resourceId);
        return ResponseEntity.noContent().build();
    }
}

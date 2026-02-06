package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.service.PolicyTargetService;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Tag(name = "Policy Targets", description = "Manage policy operation and resource targets")
public class PolicyTargetController {

    @Autowired
    private final PolicyTargetService policyTargetService;

    @PostMapping("/{policyId}/operations/{operationId}")
    @Operation(summary = "Add operation target to policy")
    public ResponseEntity<Void> addOperationToPolicy(@PathVariable  Integer policyId,
                                                   @PathVariable  Integer operationId) {
        policyTargetService.addOperationToPolicy(policyId, operationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{policyId}/operations/{operationId}")
    @Operation(summary = "Remove operation target from policy")
    public ResponseEntity<Void> removeOperationFromPolicy(@PathVariable  Integer policyId,
                                                        @PathVariable  Integer operationId) {
        policyTargetService.removeOperationFromPolicy(policyId, operationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{policyId}/resources/{resourceId}")
    @Operation(summary = "Add resource target to policy")
    public ResponseEntity<Void> addResourceToPolicy(@PathVariable  Integer policyId,
                                                     @PathVariable  Integer resourceId) {
        policyTargetService.addResourceToPolicy(policyId, resourceId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{policyId}/resource-types/{resourceTypeId}")
    @Operation(summary = "Add resource type target to policy")
    public ResponseEntity<Void> addResourceTypeToPolicy(@PathVariable Integer policyId,
                                                         @PathVariable Integer resourceTypeId) {
        policyTargetService.addResourceTypeToPolicy(policyId, resourceTypeId);
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

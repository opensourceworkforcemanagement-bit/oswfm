package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.PolicyDTO;
import org.oswfm.accesscontrolservice.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
@Tag(name = "Policies", description = "Policy management APIs")
public class PolicyController {
    
    private final PolicyService policyService;
    
    @GetMapping
    @Operation(summary = "Get all policies")
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get policy by ID")
    public ResponseEntity<PolicyDTO> getPolicyById(@PathVariable  Integer id) {
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get all active policies")
    public ResponseEntity<List<PolicyDTO>> getActivePolicies() {
        return ResponseEntity.ok(policyService.getActivePolicies());
    }
    
    @GetMapping("/type/{policyType}")
    @Operation(summary = "Get policies by type")
    public ResponseEntity<List<PolicyDTO>> getPoliciesByType(@PathVariable String policyType) {
        return ResponseEntity.ok(policyService.getPoliciesByType(policyType));
    }
    
    @PostMapping
    @Operation(summary = "Create a new policy")
    public ResponseEntity<PolicyDTO> createPolicy(@Valid @RequestBody PolicyDTO policyDTO) {
        PolicyDTO createdPolicy = policyService.createPolicy(policyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicy);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing policy")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable  Integer id, @Valid @RequestBody PolicyDTO policyDTO) {
        return ResponseEntity.ok(policyService.updatePolicy(id, policyDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a policy")
    public ResponseEntity<Void> deletePolicy(@PathVariable  Integer id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}

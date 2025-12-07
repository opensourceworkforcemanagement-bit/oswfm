package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.PolicyRuleDTO;
import org.oswfm.accesscontrolservice.service.PolicyRuleService;
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
@RequestMapping("/api/policy-rules")
@RequiredArgsConstructor
@Tag(name = "Policy Rules", description = "Policy rule management APIs")
public class PolicyRuleController {
    
    private final PolicyRuleService policyRuleService;
    
    @GetMapping
    @Operation(summary = "Get all policy rules")
    public ResponseEntity<List<PolicyRuleDTO>> getAllPolicyRules() {
        return ResponseEntity.ok(policyRuleService.getAllPolicyRules());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get policy rule by ID")
    public ResponseEntity<PolicyRuleDTO> getPolicyRuleById(@PathVariable  Integer id) {
        return ResponseEntity.ok(policyRuleService.getPolicyRuleById(id));
    }
    
    @GetMapping("/policy/{policyId}")
    @Operation(summary = "Get all rules for a specific policy")
    public ResponseEntity<List<PolicyRuleDTO>> getRulesByPolicyId(@PathVariable  Integer policyId) {
        return ResponseEntity.ok(policyRuleService.getRulesByPolicyId(policyId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new policy rule")
    public ResponseEntity<PolicyRuleDTO> createPolicyRule(@Valid @RequestBody PolicyRuleDTO policyRuleDTO) {
        PolicyRuleDTO createdPolicyRule = policyRuleService.createPolicyRule(policyRuleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicyRule);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing policy rule")
    public ResponseEntity<PolicyRuleDTO> updatePolicyRule(@PathVariable  Integer id, 
                                                          @Valid @RequestBody PolicyRuleDTO policyRuleDTO) {
        return ResponseEntity.ok(policyRuleService.updatePolicyRule(id, policyRuleDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a policy rule")
    public ResponseEntity<Void> deletePolicyRule(@PathVariable  Integer id) {
        policyRuleService.deletePolicyRule(id);
        return ResponseEntity.noContent().build();
    }
}

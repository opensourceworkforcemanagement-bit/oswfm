package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.PolicyObligationDTO;
import org.oswfm.accesscontrolservice.service.PolicyObligationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/policy-obligations")
@RequiredArgsConstructor
@Tag(name = "Policy Obligations", description = "Policy obligation management APIs")
public class PolicyObligationController {

    @Autowired
    private final PolicyObligationService policyObligationService;

    @GetMapping
    @Operation(summary = "Get all policy obligations")
    public ResponseEntity<List<PolicyObligationDTO>> getAllPolicyObligations() {
        return ResponseEntity.ok(policyObligationService.getAllPolicyObligations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get policy obligation by ID")
    public ResponseEntity<PolicyObligationDTO> getPolicyObligationById(@PathVariable Integer id) {
        return ResponseEntity.ok(policyObligationService.getPolicyObligationById(id));
    }

    @GetMapping("/policy/{policyId}")
    @Operation(summary = "Get all obligations for a specific policy")
    public ResponseEntity<List<PolicyObligationDTO>> getObligationsByPolicyId(@PathVariable Integer policyId) {
        return ResponseEntity.ok(policyObligationService.getObligationsByPolicyId(policyId));
    }

    @GetMapping("/type/{obligationTypeId}")
    @Operation(summary = "Get obligations by type")
    public ResponseEntity<List<PolicyObligationDTO>> getObligationsByType(@PathVariable Integer obligationTypeId) {
        return ResponseEntity.ok(policyObligationService.getObligationsByType(obligationTypeId));
    }

    @PostMapping
    @Operation(summary = "Create a new policy obligation")
    public ResponseEntity<PolicyObligationDTO> createPolicyObligation(@Valid @RequestBody PolicyObligationDTO obligationDTO) {
        PolicyObligationDTO createdObligation = policyObligationService.createPolicyObligation(obligationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdObligation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing policy obligation")
    public ResponseEntity<PolicyObligationDTO> updatePolicyObligation(@PathVariable Integer id,
                                                                      @Valid @RequestBody PolicyObligationDTO obligationDTO) {
        return ResponseEntity.ok(policyObligationService.updatePolicyObligation(id, obligationDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a policy obligation")
    public ResponseEntity<Void> deletePolicyObligation(@PathVariable Integer id) {
        policyObligationService.deletePolicyObligation(id);
        return ResponseEntity.noContent().build();
    }
}

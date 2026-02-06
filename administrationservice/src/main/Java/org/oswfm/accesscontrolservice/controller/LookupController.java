package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.AllLookupsResponseDTO;
import org.oswfm.accesscontrolservice.dto.LookupTypeDTO;
import org.oswfm.accesscontrolservice.service.LookupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/lookups")
@RequiredArgsConstructor
@Tag(name = "Lookups", description = "ABAC lookup type APIs")
public class LookupController {
    
    @Autowired
    private final LookupService lookupService;

    @GetMapping
    @Operation(summary = "Get all lookup types")
    public ResponseEntity<AllLookupsResponseDTO> getAllLookups() {
        return ResponseEntity.ok(lookupService.getAllLookups());
    }

    @GetMapping("/resource-types")
    @Operation(summary = "Get resource types")
    public ResponseEntity<List<LookupTypeDTO>> getResourceTypes() {
        return ResponseEntity.ok(lookupService.getResourceTypes());
    }

    @GetMapping("/attribute-categories")
    @Operation(summary = "Get attribute categories")
    public ResponseEntity<List<LookupTypeDTO>> getAttributeCategories() {
        return ResponseEntity.ok(lookupService.getAttributeCategories());
    }

    @GetMapping("/policy-types")
    @Operation(summary = "Get policy types")
    public ResponseEntity<List<LookupTypeDTO>> getPolicyTypes() {
        return ResponseEntity.ok(lookupService.getPolicyTypes());
    }

    @GetMapping("/obligation-types")
    @Operation(summary = "Get obligation types")
    public ResponseEntity<List<LookupTypeDTO>> getObligationTypes() {
        return ResponseEntity.ok(lookupService.getObligationTypes());
    }

    @GetMapping("/data-types")
    @Operation(summary = "Get data types")
    public ResponseEntity<List<LookupTypeDTO>> getDataTypes() {
        return ResponseEntity.ok(lookupService.getDataTypes());
    }
}

package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.AttributeDefinitionDTO;
import org.oswfm.accesscontrolservice.service.AttributeDefinitionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/attribute-definitions")
@RequiredArgsConstructor
@Tag(name = "Attribute Definitions", description = "Attribute definition management APIs")
public class AttributeDefinitionController {
    
    @Autowired
    private final AttributeDefinitionService attributeDefinitionService;
    
    @GetMapping
    @Operation(summary = "Get all attribute definitions")
    public ResponseEntity<List<AttributeDefinitionDTO>> getAllAttributeDefinitions() {
        return ResponseEntity.ok(attributeDefinitionService.getAllAttributeDefinitions());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get attribute definition by ID")
    public ResponseEntity<AttributeDefinitionDTO> getAttributeDefinitionById(@PathVariable  Integer id) {
        return ResponseEntity.ok(attributeDefinitionService.getAttributeDefinitionById(id));
    }
    
    @GetMapping("/name/{attributeName}")
    @Operation(summary = "Get attribute definition by name")
    public ResponseEntity<AttributeDefinitionDTO> getAttributeDefinitionByName(@PathVariable String attributeName) {
        return ResponseEntity.ok(attributeDefinitionService.getAttributeDefinitionByName(attributeName));
    }
    
    @GetMapping("/category/{attributeCategoryId}")
    @Operation(summary = "Get attribute definitions by category")
    public ResponseEntity<List<AttributeDefinitionDTO>> getAttributeDefinitionsByCategory(@PathVariable Integer attributeCategoryId) {
        return ResponseEntity.ok(attributeDefinitionService.getAttributeDefinitionsByCategory(attributeCategoryId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new attribute definition")
    public ResponseEntity<AttributeDefinitionDTO> createAttributeDefinition(@Valid @RequestBody AttributeDefinitionDTO attributeDefinitionDTO) {
        AttributeDefinitionDTO createdAttributeDefinition = attributeDefinitionService.createAttributeDefinition(attributeDefinitionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttributeDefinition);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing attribute definition")
    public ResponseEntity<AttributeDefinitionDTO> updateAttributeDefinition(@PathVariable  Integer id, @Valid @RequestBody AttributeDefinitionDTO attributeDefinitionDTO) {
        return ResponseEntity.ok(attributeDefinitionService.updateAttributeDefinition(id, attributeDefinitionDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an attribute definition")
    public ResponseEntity<Void> deleteAttributeDefinition(@PathVariable  Integer id) {
        attributeDefinitionService.deleteAttributeDefinition(id);
        return ResponseEntity.noContent().build();
    }
}

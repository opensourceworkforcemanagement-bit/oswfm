package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.ResourceAttributeDTO;
import org.oswfm.accesscontrolservice.service.ResourceAttributeService;
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
@RequestMapping("/api/resource-attributes")
@RequiredArgsConstructor
@Tag(name = "Resource Attributes", description = "Resource attribute management APIs")
public class ResourceAttributeController {
    
    private final ResourceAttributeService resourceAttributeService;
    
    @GetMapping
    @Operation(summary = "Get all resource attributes")
    public ResponseEntity<List<ResourceAttributeDTO>> getAllResourceAttributes() {
        return ResponseEntity.ok(resourceAttributeService.getAllResourceAttributes());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get resource attribute by ID")
    public ResponseEntity<ResourceAttributeDTO> getResourceAttributeById(@PathVariable  Integer id) {
        return ResponseEntity.ok(resourceAttributeService.getResourceAttributeById(id));
    }
    
    @GetMapping("/resource/{resourceId}")
    @Operation(summary = "Get resource attributes by resource ID")
    public ResponseEntity<List<ResourceAttributeDTO>> getResourceAttributesByResourceId(@PathVariable  Integer resourceId) {
        return ResponseEntity.ok(resourceAttributeService.getResourceAttributesByResourceId(resourceId));
    }
    
    @GetMapping("/resource/{resourceId}/active")
    @Operation(summary = "Get active resource attributes by resource ID")
    public ResponseEntity<List<ResourceAttributeDTO>> getActiveResourceAttributesByResourceId(@PathVariable  Integer resourceId) {
        return ResponseEntity.ok(resourceAttributeService.getActiveResourceAttributesByResourceId(resourceId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new resource attribute")
    public ResponseEntity<ResourceAttributeDTO> createResourceAttribute(@Valid @RequestBody ResourceAttributeDTO resourceAttributeDTO) {
        ResourceAttributeDTO createdResourceAttribute = resourceAttributeService.createResourceAttribute(resourceAttributeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResourceAttribute);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing resource attribute")
    public ResponseEntity<ResourceAttributeDTO> updateResourceAttribute(@PathVariable  Integer id, @Valid @RequestBody ResourceAttributeDTO resourceAttributeDTO) {
        return ResponseEntity.ok(resourceAttributeService.updateResourceAttribute(id, resourceAttributeDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a resource attribute")
    public ResponseEntity<Void> deleteResourceAttribute(@PathVariable  Integer id) {
        resourceAttributeService.deleteResourceAttribute(id);
        return ResponseEntity.noContent().build();
    }
}

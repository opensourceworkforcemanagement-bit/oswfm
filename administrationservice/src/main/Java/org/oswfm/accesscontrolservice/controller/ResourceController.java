package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.ResourceDTO;
import org.oswfm.accesscontrolservice.service.ResourceService;

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
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
@Tag(name = "Resources", description = "Resource management APIs")
public class ResourceController {
    
    @Autowired
    private final ResourceService resourceService;
    
    @GetMapping
    @Operation(summary = "Get all resources")
    public ResponseEntity<List<ResourceDTO>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get resource by ID")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable  Integer id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }
    
    @GetMapping("/type/{resourceTypeId}")
    @Operation(summary = "Get resources by type")
    public ResponseEntity<List<ResourceDTO>> getResourcesByType(@PathVariable Integer resourceTypeId) {
        return ResponseEntity.ok(resourceService.getResourcesByType(resourceTypeId));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get all active resources")
    public ResponseEntity<List<ResourceDTO>> getActiveResources() {
        return ResponseEntity.ok(resourceService.getActiveResources());
    }
    
    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get resources by owner ID")
    public ResponseEntity<List<ResourceDTO>> getResourcesByOwner(@PathVariable  Integer ownerId) {
        return ResponseEntity.ok(resourceService.getResourcesByOwner(ownerId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new resource")
    public ResponseEntity<ResourceDTO> createResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        ResourceDTO createdResource = resourceService.createResource(resourceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing resource")
    public ResponseEntity<ResourceDTO> updateResource(@PathVariable  Integer id, @Valid @RequestBody ResourceDTO resourceDTO) {
        return ResponseEntity.ok(resourceService.updateResource(id, resourceDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a resource")
    public ResponseEntity<Void> deleteResource(@PathVariable  Integer id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}

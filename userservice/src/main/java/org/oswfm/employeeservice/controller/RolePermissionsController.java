package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.RolePermissionsRequestDTO;
import org.oswfm.employeeservice.model.dto.RolePermissionsResponseDTO;
import org.oswfm.employeeservice.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
@CrossOrigin(origins = "*")
public class RolePermissionsController {

    @Autowired
    private RolePermissionsService service;

    /**
     * Create a new RolePermissions
     * POST /api/role-permissions
     */
    @PostMapping
    public ResponseEntity<RolePermissionsResponseDTO> create(@Valid @RequestBody RolePermissionsRequestDTO requestDTO) {
        RolePermissionsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get RolePermissions by ID
     * GET /api/role-permissions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all RolePermissionss
     * GET /api/role-permissions
     */
    @GetMapping
    public ResponseEntity<List<RolePermissionsResponseDTO>> getAll() {
        List<RolePermissionsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update RolePermissions by ID
     * PUT /api/role-permissions/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RolePermissionsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody RolePermissionsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete RolePermissions by ID
     * DELETE /api/role-permissions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if RolePermissions exists by ID
     * HEAD /api/role-permissions/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all RolePermissionss
     * GET /api/role-permissions/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

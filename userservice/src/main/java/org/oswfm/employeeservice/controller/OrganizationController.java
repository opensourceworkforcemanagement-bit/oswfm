package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.OrganizationRequestDTO;
import org.oswfm.employeeservice.model.dto.OrganizationResponseDTO;
import org.oswfm.employeeservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@CrossOrigin(origins = "*")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    /**
     * Create a new Organization
     * POST /api/organization
     */
    @PostMapping
    public ResponseEntity<OrganizationResponseDTO> create(@Valid @RequestBody OrganizationRequestDTO requestDTO) {
        OrganizationResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Organization by ID
     * GET /api/organization/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Organizations
     * GET /api/organization
     */
    @GetMapping
    public ResponseEntity<List<OrganizationResponseDTO>> getAll() {
        List<OrganizationResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Organization by ID
     * PUT /api/organization/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrganizationRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Organization by ID
     * DELETE /api/organization/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Organization exists by ID
     * HEAD /api/organization/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Organizations
     * GET /api/organization/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

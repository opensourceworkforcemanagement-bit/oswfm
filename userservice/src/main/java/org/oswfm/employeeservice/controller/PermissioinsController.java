package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.PermissioinsRequestDTO;
import org.oswfm.employeeservice.model.dto.PermissioinsResponseDTO;
import org.oswfm.employeeservice.service.PermissioinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/permissioins")
@CrossOrigin(origins = "*")
public class PermissioinsController {

    @Autowired
    private PermissioinsService service;

    /**
     * Create a new Permissioins
     * POST /api/permissioins
     */
    @PostMapping
    public ResponseEntity<PermissioinsResponseDTO> create(@Valid @RequestBody PermissioinsRequestDTO requestDTO) {
        PermissioinsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Permissioins by ID
     * GET /api/permissioins/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissioinsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Permissioinss
     * GET /api/permissioins
     */
    @GetMapping
    public ResponseEntity<List<PermissioinsResponseDTO>> getAll() {
        List<PermissioinsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Permissioins by ID
     * PUT /api/permissioins/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermissioinsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PermissioinsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Permissioins by ID
     * DELETE /api/permissioins/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Permissioins exists by ID
     * HEAD /api/permissioins/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Permissioinss
     * GET /api/permissioins/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

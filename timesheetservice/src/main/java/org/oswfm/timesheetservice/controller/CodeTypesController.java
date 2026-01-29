package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.CodeTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.CodeTypesResponseDTO;
import org.oswfm.timesheetservice.service.CodeTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/code-types")
public class CodeTypesController {

    @Autowired
    private CodeTypesService service;

    /**
     * Create a new CodeTypes
     * POST /api/v1/code-types/create
     */
    @PostMapping("/create")
    public ResponseEntity<CodeTypesResponseDTO> create(@Valid @RequestBody CodeTypesRequestDTO requestDTO) {
        CodeTypesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get CodeTypes by ID
     * GET /api/v1/code-types/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodeTypesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get CodeTypes by name
     * GET /api/v1/code-types/name/{name}
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CodeTypesResponseDTO> getByName(@PathVariable String name) {
        return service.getByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all CodeTypes
     * GET /api/v1/code-types
     */
    @GetMapping
    public ResponseEntity<List<CodeTypesResponseDTO>> getAll() {
        List<CodeTypesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update CodeTypes by ID
     * PUT /api/v1/code-types/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodeTypesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CodeTypesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete CodeTypes by ID
     * DELETE /api/v1/code-types/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if CodeTypes exists by ID
     * HEAD /api/v1/code-types/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all CodeTypes
     * GET /api/v1/code-types/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

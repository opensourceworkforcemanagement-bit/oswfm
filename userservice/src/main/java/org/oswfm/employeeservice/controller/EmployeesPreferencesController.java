package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesPreferencesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesPreferencesResponseDTO;
import org.oswfm.employeeservice.service.EmployeesPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-preferences")
@CrossOrigin(origins = "*")
public class EmployeesPreferencesController {

    @Autowired
    private EmployeesPreferencesService service;

    /**
     * Create a new EmployeesPreferences
     * POST /api/employees-preferences
     */
    @PostMapping
    public ResponseEntity<EmployeesPreferencesResponseDTO> create(@Valid @RequestBody EmployeesPreferencesRequestDTO requestDTO) {
        EmployeesPreferencesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesPreferences by ID
     * GET /api/employees-preferences/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesPreferencesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesPreferencess
     * GET /api/employees-preferences
     */
    @GetMapping
    public ResponseEntity<List<EmployeesPreferencesResponseDTO>> getAll() {
        List<EmployeesPreferencesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesPreferences by ID
     * PUT /api/employees-preferences/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesPreferencesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesPreferencesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesPreferences by ID
     * DELETE /api/employees-preferences/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesPreferences exists by ID
     * HEAD /api/employees-preferences/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesPreferencess
     * GET /api/employees-preferences/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

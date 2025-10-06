package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesSettingsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSettingsResponseDTO;
import org.oswfm.employeeservice.service.EmployeesSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-settings")
@CrossOrigin(origins = "*")
public class EmployeesSettingsController {

    @Autowired
    private EmployeesSettingsService service;

    /**
     * Create a new EmployeesSettings
     * POST /api/employees-settings
     */
    @PostMapping
    public ResponseEntity<EmployeesSettingsResponseDTO> create(@Valid @RequestBody EmployeesSettingsRequestDTO requestDTO) {
        EmployeesSettingsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesSettings by ID
     * GET /api/employees-settings/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesSettingsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesSettingss
     * GET /api/employees-settings
     */
    @GetMapping
    public ResponseEntity<List<EmployeesSettingsResponseDTO>> getAll() {
        List<EmployeesSettingsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesSettings by ID
     * PUT /api/employees-settings/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesSettingsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesSettingsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesSettings by ID
     * DELETE /api/employees-settings/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesSettings exists by ID
     * HEAD /api/employees-settings/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesSettingss
     * GET /api/employees-settings/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.employeeservice.controller;

import java.util.List;

import org.oswfm.employeeservice.model.dto.EmployeesAuditLogRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesAuditLogResponseDTO;
import org.oswfm.employeeservice.service.EmployeesAuditLogService;
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
@RequestMapping("/api/v1/employees-audit-log")
public class EmployeesAuditLogController {

    @Autowired
    private EmployeesAuditLogService service;

    /**
     * Create a new EmployeesAuditLog
     * POST /api/employees-audit-log
     */
    @PostMapping
    public ResponseEntity<EmployeesAuditLogResponseDTO> create(@Valid @RequestBody EmployeesAuditLogRequestDTO requestDTO) {
        EmployeesAuditLogResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesAuditLog by ID
     * GET /api/employees-audit-log/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesAuditLogResponseDTO> getById(@PathVariable Integer  id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesAuditLogs
     * GET /api/employees-audit-log
     */
    @GetMapping
    public ResponseEntity<List<EmployeesAuditLogResponseDTO>> getAll() {
        List<EmployeesAuditLogResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesAuditLog by ID
     * PUT /api/employees-audit-log/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesAuditLogResponseDTO> update(
            @PathVariable Integer  id,
            @Valid @RequestBody EmployeesAuditLogRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesAuditLog by ID
     * DELETE /api/employees-audit-log/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer  id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesAuditLog exists by ID
     * HEAD /api/employees-audit-log/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer  id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesAuditLogs
     * GET /api/employees-audit-log/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-audit-log")
@CrossOrigin(origins = "*")
public class TimesheetAuditLogController {

    @Autowired
    private TimesheetAuditLogService service;

    /**
     * Create a new TimesheetAuditLog
     * POST /api/timesheet-audit-log
     */
    @PostMapping
    public ResponseEntity<TimesheetAuditLogResponseDTO> create(@Valid @RequestBody TimesheetAuditLogRequestDTO requestDTO) {
        TimesheetAuditLogResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetAuditLog by ID
     * GET /api/timesheet-audit-log/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetAuditLogResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetAuditLogs
     * GET /api/timesheet-audit-log
     */
    @GetMapping
    public ResponseEntity<List<TimesheetAuditLogResponseDTO>> getAll() {
        List<TimesheetAuditLogResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetAuditLog by ID
     * PUT /api/timesheet-audit-log/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetAuditLogResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetAuditLogRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetAuditLog by ID
     * DELETE /api/timesheet-audit-log/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetAuditLog exists by ID
     * HEAD /api/timesheet-audit-log/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetAuditLogs
     * GET /api/timesheet-audit-log/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

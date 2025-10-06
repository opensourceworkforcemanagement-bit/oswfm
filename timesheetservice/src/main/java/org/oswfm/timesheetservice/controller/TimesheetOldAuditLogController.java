package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetOldAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-old-audit-log")
@CrossOrigin(origins = "*")
public class TimesheetOldAuditLogController {

    @Autowired
    private TimesheetOldAuditLogService service;

    /**
     * Create a new TimesheetOldAuditLog
     * POST /api/timesheet-old-audit-log
     */
    @PostMapping
    public ResponseEntity<TimesheetOldAuditLogResponseDTO> create(@Valid @RequestBody TimesheetOldAuditLogRequestDTO requestDTO) {
        TimesheetOldAuditLogResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetOldAuditLog by ID
     * GET /api/timesheet-old-audit-log/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetOldAuditLogResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetOldAuditLogs
     * GET /api/timesheet-old-audit-log
     */
    @GetMapping
    public ResponseEntity<List<TimesheetOldAuditLogResponseDTO>> getAll() {
        List<TimesheetOldAuditLogResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetOldAuditLog by ID
     * PUT /api/timesheet-old-audit-log/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetOldAuditLogResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetOldAuditLogRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetOldAuditLog by ID
     * DELETE /api/timesheet-old-audit-log/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetOldAuditLog exists by ID
     * HEAD /api/timesheet-old-audit-log/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetOldAuditLogs
     * GET /api/timesheet-old-audit-log/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

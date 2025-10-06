package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetAuditLogArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-audit-log-archive")
@CrossOrigin(origins = "*")
public class TimesheetAuditLogArchiveController {

    @Autowired
    private TimesheetAuditLogArchiveService service;

    /**
     * Create a new TimesheetAuditLogArchive
     * POST /api/timesheet-audit-log-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetAuditLogArchiveResponseDTO> create(@Valid @RequestBody TimesheetAuditLogArchiveRequestDTO requestDTO) {
        TimesheetAuditLogArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetAuditLogArchive by ID
     * GET /api/timesheet-audit-log-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetAuditLogArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetAuditLogArchives
     * GET /api/timesheet-audit-log-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetAuditLogArchiveResponseDTO>> getAll() {
        List<TimesheetAuditLogArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetAuditLogArchive by ID
     * PUT /api/timesheet-audit-log-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetAuditLogArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetAuditLogArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetAuditLogArchive by ID
     * DELETE /api/timesheet-audit-log-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetAuditLogArchive exists by ID
     * HEAD /api/timesheet-audit-log-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetAuditLogArchives
     * GET /api/timesheet-audit-log-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

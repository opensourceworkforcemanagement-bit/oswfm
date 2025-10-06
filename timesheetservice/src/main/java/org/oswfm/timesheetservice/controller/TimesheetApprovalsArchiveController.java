package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetApprovalsArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-approvals-archive")
@CrossOrigin(origins = "*")
public class TimesheetApprovalsArchiveController {

    @Autowired
    private TimesheetApprovalsArchiveService service;

    /**
     * Create a new TimesheetApprovalsArchive
     * POST /api/timesheet-approvals-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetApprovalsArchiveResponseDTO> create(@Valid @RequestBody TimesheetApprovalsArchiveRequestDTO requestDTO) {
        TimesheetApprovalsArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetApprovalsArchive by ID
     * GET /api/timesheet-approvals-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetApprovalsArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetApprovalsArchives
     * GET /api/timesheet-approvals-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetApprovalsArchiveResponseDTO>> getAll() {
        List<TimesheetApprovalsArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetApprovalsArchive by ID
     * PUT /api/timesheet-approvals-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetApprovalsArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetApprovalsArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetApprovalsArchive by ID
     * DELETE /api/timesheet-approvals-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetApprovalsArchive exists by ID
     * HEAD /api/timesheet-approvals-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetApprovalsArchives
     * GET /api/timesheet-approvals-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

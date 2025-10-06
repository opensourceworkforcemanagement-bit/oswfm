package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetRemarksArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-remarks-archive")
@CrossOrigin(origins = "*")
public class TimesheetRemarksArchiveController {

    @Autowired
    private TimesheetRemarksArchiveService service;

    /**
     * Create a new TimesheetRemarksArchive
     * POST /api/timesheet-remarks-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetRemarksArchiveResponseDTO> create(@Valid @RequestBody TimesheetRemarksArchiveRequestDTO requestDTO) {
        TimesheetRemarksArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetRemarksArchive by ID
     * GET /api/timesheet-remarks-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetRemarksArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetRemarksArchives
     * GET /api/timesheet-remarks-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetRemarksArchiveResponseDTO>> getAll() {
        List<TimesheetRemarksArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetRemarksArchive by ID
     * PUT /api/timesheet-remarks-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetRemarksArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetRemarksArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetRemarksArchive by ID
     * DELETE /api/timesheet-remarks-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetRemarksArchive exists by ID
     * HEAD /api/timesheet-remarks-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetRemarksArchives
     * GET /api/timesheet-remarks-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

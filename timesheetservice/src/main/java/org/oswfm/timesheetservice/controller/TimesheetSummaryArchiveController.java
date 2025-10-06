package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetSummaryArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-summary-archive")
@CrossOrigin(origins = "*")
public class TimesheetSummaryArchiveController {

    @Autowired
    private TimesheetSummaryArchiveService service;

    /**
     * Create a new TimesheetSummaryArchive
     * POST /api/timesheet-summary-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetSummaryArchiveResponseDTO> create(@Valid @RequestBody TimesheetSummaryArchiveRequestDTO requestDTO) {
        TimesheetSummaryArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetSummaryArchive by ID
     * GET /api/timesheet-summary-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetSummaryArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetSummaryArchives
     * GET /api/timesheet-summary-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetSummaryArchiveResponseDTO>> getAll() {
        List<TimesheetSummaryArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetSummaryArchive by ID
     * PUT /api/timesheet-summary-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetSummaryArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetSummaryArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetSummaryArchive by ID
     * DELETE /api/timesheet-summary-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetSummaryArchive exists by ID
     * HEAD /api/timesheet-summary-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetSummaryArchives
     * GET /api/timesheet-summary-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

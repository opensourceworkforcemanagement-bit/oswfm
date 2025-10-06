package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesCommentsArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-comments-archive")
@CrossOrigin(origins = "*")
public class TimesheetEntriesCommentsArchiveController {

    @Autowired
    private TimesheetEntriesCommentsArchiveService service;

    /**
     * Create a new TimesheetEntriesCommentsArchive
     * POST /api/timesheet-entries-comments-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesCommentsArchiveResponseDTO> create(@Valid @RequestBody TimesheetEntriesCommentsArchiveRequestDTO requestDTO) {
        TimesheetEntriesCommentsArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesCommentsArchive by ID
     * GET /api/timesheet-entries-comments-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesCommentsArchives
     * GET /api/timesheet-entries-comments-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesCommentsArchiveResponseDTO>> getAll() {
        List<TimesheetEntriesCommentsArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesCommentsArchive by ID
     * PUT /api/timesheet-entries-comments-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesCommentsArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesCommentsArchive by ID
     * DELETE /api/timesheet-entries-comments-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesCommentsArchive exists by ID
     * HEAD /api/timesheet-entries-comments-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesCommentsArchives
     * GET /api/timesheet-entries-comments-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-archive")
@CrossOrigin(origins = "*")
public class TimesheetEntriesArchiveController {

    @Autowired
    private TimesheetEntriesArchiveService service;

    /**
     * Create a new TimesheetEntriesArchive
     * POST /api/timesheet-entries-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesArchiveResponseDTO> create(@Valid @RequestBody TimesheetEntriesArchiveRequestDTO requestDTO) {
        TimesheetEntriesArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesArchive by ID
     * GET /api/timesheet-entries-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesArchives
     * GET /api/timesheet-entries-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesArchiveResponseDTO>> getAll() {
        List<TimesheetEntriesArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesArchive by ID
     * PUT /api/timesheet-entries-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesArchive by ID
     * DELETE /api/timesheet-entries-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesArchive exists by ID
     * HEAD /api/timesheet-entries-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesArchives
     * GET /api/timesheet-entries-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesInOutArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-in-out-archive")
@CrossOrigin(origins = "*")
public class TimesheetEntriesInOutArchiveController {

    @Autowired
    private TimesheetEntriesInOutArchiveService service;

    /**
     * Create a new TimesheetEntriesInOutArchive
     * POST /api/timesheet-entries-in-out-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesInOutArchiveResponseDTO> create(@Valid @RequestBody TimesheetEntriesInOutArchiveRequestDTO requestDTO) {
        TimesheetEntriesInOutArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesInOutArchive by ID
     * GET /api/timesheet-entries-in-out-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesInOutArchives
     * GET /api/timesheet-entries-in-out-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesInOutArchiveResponseDTO>> getAll() {
        List<TimesheetEntriesInOutArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesInOutArchive by ID
     * PUT /api/timesheet-entries-in-out-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesInOutArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesInOutArchive by ID
     * DELETE /api/timesheet-entries-in-out-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesInOutArchive exists by ID
     * HEAD /api/timesheet-entries-in-out-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesInOutArchives
     * GET /api/timesheet-entries-in-out-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

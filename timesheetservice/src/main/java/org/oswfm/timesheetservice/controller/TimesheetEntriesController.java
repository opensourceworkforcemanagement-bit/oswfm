package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries")
@CrossOrigin(origins = "*")
public class TimesheetEntriesController {

    @Autowired
    private TimesheetEntriesService service;

    /**
     * Create a new TimesheetEntries
     * POST /api/timesheet-entries
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesResponseDTO> create(@Valid @RequestBody TimesheetEntriesRequestDTO requestDTO) {
        TimesheetEntriesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntries by ID
     * GET /api/timesheet-entries/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriess
     * GET /api/timesheet-entries
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesResponseDTO>> getAll() {
        List<TimesheetEntriesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntries by ID
     * PUT /api/timesheet-entries/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntries by ID
     * DELETE /api/timesheet-entries/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntries exists by ID
     * HEAD /api/timesheet-entries/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriess
     * GET /api/timesheet-entries/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

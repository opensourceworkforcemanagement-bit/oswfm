package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesCommentsOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-comments-old")
@CrossOrigin(origins = "*")
public class TimesheetEntriesCommentsOldController {

    @Autowired
    private TimesheetEntriesCommentsOldService service;

    /**
     * Create a new TimesheetEntriesCommentsOld
     * POST /api/timesheet-entries-comments-old
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesCommentsOldResponseDTO> create(@Valid @RequestBody TimesheetEntriesCommentsOldRequestDTO requestDTO) {
        TimesheetEntriesCommentsOldResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesCommentsOld by ID
     * GET /api/timesheet-entries-comments-old/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsOldResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesCommentsOlds
     * GET /api/timesheet-entries-comments-old
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesCommentsOldResponseDTO>> getAll() {
        List<TimesheetEntriesCommentsOldResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesCommentsOld by ID
     * PUT /api/timesheet-entries-comments-old/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsOldResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesCommentsOldRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesCommentsOld by ID
     * DELETE /api/timesheet-entries-comments-old/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesCommentsOld exists by ID
     * HEAD /api/timesheet-entries-comments-old/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesCommentsOlds
     * GET /api/timesheet-entries-comments-old/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-comments")
@CrossOrigin(origins = "*")
public class TimesheetEntriesCommentsController {

    @Autowired
    private TimesheetEntriesCommentsService service;

    /**
     * Create a new TimesheetEntriesComments
     * POST /api/timesheet-entries-comments
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesCommentsResponseDTO> create(@Valid @RequestBody TimesheetEntriesCommentsRequestDTO requestDTO) {
        TimesheetEntriesCommentsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesComments by ID
     * GET /api/timesheet-entries-comments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesCommentss
     * GET /api/timesheet-entries-comments
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesCommentsResponseDTO>> getAll() {
        List<TimesheetEntriesCommentsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesComments by ID
     * PUT /api/timesheet-entries-comments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesCommentsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesCommentsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesComments by ID
     * DELETE /api/timesheet-entries-comments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesComments exists by ID
     * HEAD /api/timesheet-entries-comments/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesCommentss
     * GET /api/timesheet-entries-comments/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

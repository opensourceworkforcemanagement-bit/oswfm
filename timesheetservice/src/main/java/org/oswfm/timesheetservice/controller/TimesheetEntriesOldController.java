package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-old")
@CrossOrigin(origins = "*")
public class TimesheetEntriesOldController {

    @Autowired
    private TimesheetEntriesOldService service;

    /**
     * Create a new TimesheetEntriesOld
     * POST /api/timesheet-entries-old
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesOldResponseDTO> create(@Valid @RequestBody TimesheetEntriesOldRequestDTO requestDTO) {
        TimesheetEntriesOldResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesOld by ID
     * GET /api/timesheet-entries-old/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesOldResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesOlds
     * GET /api/timesheet-entries-old
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesOldResponseDTO>> getAll() {
        List<TimesheetEntriesOldResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesOld by ID
     * PUT /api/timesheet-entries-old/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesOldResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesOldRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesOld by ID
     * DELETE /api/timesheet-entries-old/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesOld exists by ID
     * HEAD /api/timesheet-entries-old/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesOlds
     * GET /api/timesheet-entries-old/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

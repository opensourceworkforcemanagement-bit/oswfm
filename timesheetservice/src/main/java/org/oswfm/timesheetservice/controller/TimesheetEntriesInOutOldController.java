package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutOldResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesInOutOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-in-out-old")
@CrossOrigin(origins = "*")
public class TimesheetEntriesInOutOldController {

    @Autowired
    private TimesheetEntriesInOutOldService service;

    /**
     * Create a new TimesheetEntriesInOutOld
     * POST /api/timesheet-entries-in-out-old
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesInOutOldResponseDTO> create(@Valid @RequestBody TimesheetEntriesInOutOldRequestDTO requestDTO) {
        TimesheetEntriesInOutOldResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesInOutOld by ID
     * GET /api/timesheet-entries-in-out-old/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutOldResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesInOutOlds
     * GET /api/timesheet-entries-in-out-old
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesInOutOldResponseDTO>> getAll() {
        List<TimesheetEntriesInOutOldResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesInOutOld by ID
     * PUT /api/timesheet-entries-in-out-old/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutOldResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesInOutOldRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesInOutOld by ID
     * DELETE /api/timesheet-entries-in-out-old/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesInOutOld exists by ID
     * HEAD /api/timesheet-entries-in-out-old/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesInOutOlds
     * GET /api/timesheet-entries-in-out-old/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

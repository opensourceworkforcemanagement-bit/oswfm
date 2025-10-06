package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetNotificationsArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-notifications-archive")
@CrossOrigin(origins = "*")
public class TimesheetNotificationsArchiveController {

    @Autowired
    private TimesheetNotificationsArchiveService service;

    /**
     * Create a new TimesheetNotificationsArchive
     * POST /api/timesheet-notifications-archive
     */
    @PostMapping
    public ResponseEntity<TimesheetNotificationsArchiveResponseDTO> create(@Valid @RequestBody TimesheetNotificationsArchiveRequestDTO requestDTO) {
        TimesheetNotificationsArchiveResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetNotificationsArchive by ID
     * GET /api/timesheet-notifications-archive/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetNotificationsArchiveResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetNotificationsArchives
     * GET /api/timesheet-notifications-archive
     */
    @GetMapping
    public ResponseEntity<List<TimesheetNotificationsArchiveResponseDTO>> getAll() {
        List<TimesheetNotificationsArchiveResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetNotificationsArchive by ID
     * PUT /api/timesheet-notifications-archive/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetNotificationsArchiveResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetNotificationsArchiveRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetNotificationsArchive by ID
     * DELETE /api/timesheet-notifications-archive/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetNotificationsArchive exists by ID
     * HEAD /api/timesheet-notifications-archive/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetNotificationsArchives
     * GET /api/timesheet-notifications-archive/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-notifications")
@CrossOrigin(origins = "*")
public class TimesheetNotificationsController {

    @Autowired
    private TimesheetNotificationsService service;

    /**
     * Create a new TimesheetNotifications
     * POST /api/timesheet-notifications
     */
    @PostMapping
    public ResponseEntity<TimesheetNotificationsResponseDTO> create(@Valid @RequestBody TimesheetNotificationsRequestDTO requestDTO) {
        TimesheetNotificationsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetNotifications by ID
     * GET /api/timesheet-notifications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetNotificationsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetNotificationss
     * GET /api/timesheet-notifications
     */
    @GetMapping
    public ResponseEntity<List<TimesheetNotificationsResponseDTO>> getAll() {
        List<TimesheetNotificationsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetNotifications by ID
     * PUT /api/timesheet-notifications/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetNotificationsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetNotificationsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetNotifications by ID
     * DELETE /api/timesheet-notifications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetNotifications exists by ID
     * HEAD /api/timesheet-notifications/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetNotificationss
     * GET /api/timesheet-notifications/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

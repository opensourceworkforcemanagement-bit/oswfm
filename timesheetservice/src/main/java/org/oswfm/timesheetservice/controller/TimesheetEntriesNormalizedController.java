package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesNormalizedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/timesheet-entries-normalized")
public class TimesheetEntriesNormalizedController {

    @Autowired
    private TimesheetEntriesNormalizedService service;

    /**
     * Create a new TimesheetEntriesNormalized
     * POST /api/v1/timesheet-entries-normalized/create
     */
    @PostMapping("/create")
    public ResponseEntity<TimesheetEntriesNormalizedResponseDTO> create(@Valid @RequestBody TimesheetEntriesNormalizedRequestDTO requestDTO) {
        TimesheetEntriesNormalizedResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesNormalized by ID
     * GET /api/v1/timesheet-entries-normalized/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesNormalizedResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesNormalized with optional filters
     * GET /api/v1/timesheet-entries-normalized
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesNormalizedResponseDTO>> getAll(
            @RequestParam(required = false) Long timesheetNormalizedId) {

        List<TimesheetEntriesNormalizedResponseDTO> list;

        if (timesheetNormalizedId != null) {
            list = service.getByTimesheetNormalizedId(timesheetNormalizedId);
        } else {
            list = service.getAll();
        }

        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesNormalized by ID
     * PUT /api/v1/timesheet-entries-normalized/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesNormalizedResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TimesheetEntriesNormalizedRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Add workforce code to entry
     * POST /api/v1/timesheet-entries-normalized/{id}/workforce-codes/{workforceCodeId}
     */
    @PostMapping("/{id}/workforce-codes/{workforceCodeId}")
    public ResponseEntity<TimesheetEntriesNormalizedResponseDTO> addWorkforceCode(
            @PathVariable Long id,
            @PathVariable Long workforceCodeId) {
        return service.addWorkforceCode(id, workforceCodeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove workforce code from entry
     * DELETE /api/v1/timesheet-entries-normalized/{id}/workforce-codes/{workforceCodeId}
     */
    @DeleteMapping("/{id}/workforce-codes/{workforceCodeId}")
    public ResponseEntity<TimesheetEntriesNormalizedResponseDTO> removeWorkforceCode(
            @PathVariable Long id,
            @PathVariable Long workforceCodeId) {
        return service.removeWorkforceCode(id, workforceCodeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesNormalized by ID
     * DELETE /api/v1/timesheet-entries-normalized/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesNormalized exists by ID
     * HEAD /api/v1/timesheet-entries-normalized/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Long id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesNormalized
     * GET /api/v1/timesheet-entries-normalized/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

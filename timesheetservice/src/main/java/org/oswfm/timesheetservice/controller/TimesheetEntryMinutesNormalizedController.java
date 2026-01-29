package org.oswfm.timesheetservice.controller;

import java.util.Date;
import java.util.List;

import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntryMinutesNormalizedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api/v1/timesheet-entry-minutes")
public class TimesheetEntryMinutesNormalizedController {

    @Autowired
    private TimesheetEntryMinutesNormalizedService service;

    /**
     * Create a new TimesheetEntryMinutes
     * POST /api/v1/timesheet-entry-minutes/create
     */
    @PostMapping("/create")
    public ResponseEntity<TimesheetEntryMinutesResponseDTO> create(@Valid @RequestBody TimesheetEntryMinutesRequestDTO requestDTO) {
        TimesheetEntryMinutesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Create multiple TimesheetEntryMinutes in batch
     * POST /api/v1/timesheet-entry-minutes/batch
     */
    @PostMapping("/batch")
    public ResponseEntity<List<TimesheetEntryMinutesResponseDTO>> createBatch(@Valid @RequestBody List<TimesheetEntryMinutesRequestDTO> requestDTOs) {
        List<TimesheetEntryMinutesResponseDTO> created = service.createBatch(requestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntryMinutes by ID
     * GET /api/v1/timesheet-entry-minutes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntryMinutesResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntryMinutes with optional filters
     * GET /api/v1/timesheet-entry-minutes
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntryMinutesResponseDTO>> getAll(
            @RequestParam(required = false) Long timesheetEntriesId,
            @RequestParam(required = false) String dayOfWeek,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        List<TimesheetEntryMinutesResponseDTO> list;

        if (timesheetEntriesId != null && dayOfWeek != null) {
            list = service.getByTimesheetEntriesIdAndDayOfWeek(timesheetEntriesId, dayOfWeek);
        } else if (timesheetEntriesId != null && date != null) {
            list = service.getByTimesheetEntriesIdAndDate(timesheetEntriesId, date);
        } else if (timesheetEntriesId != null) {
            list = service.getByTimesheetEntriesId(timesheetEntriesId);
        } else {
            list = service.getAll();
        }

        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntryMinutes by ID
     * PUT /api/v1/timesheet-entry-minutes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntryMinutesResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TimesheetEntryMinutesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntryMinutes by ID
     * DELETE /api/v1/timesheet-entry-minutes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete all minutes for a timesheet entry
     * DELETE /api/v1/timesheet-entry-minutes/entry/{timesheetEntriesId}
     */
    @DeleteMapping("/entry/{timesheetEntriesId}")
    public ResponseEntity<Void> deleteByTimesheetEntriesId(@PathVariable Long timesheetEntriesId) {
        service.deleteByTimesheetEntriesId(timesheetEntriesId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if TimesheetEntryMinutes exists by ID
     * HEAD /api/v1/timesheet-entry-minutes/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Long id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntryMinutes
     * GET /api/v1/timesheet-entry-minutes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.TimesheetEntryCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryCodesResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntryCodesNormalizedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/timesheet-entry-codes")
public class TimesheetEntryCodesNormalizedController {

    @Autowired
    private TimesheetEntryCodesNormalizedService service;

    /**
     * Create a new association between timesheet entry and workforce code
     * POST /api/v1/timesheet-entry-codes/create
     */
    @PostMapping("/create")
    public ResponseEntity<TimesheetEntryCodesResponseDTO> create(@Valid @RequestBody TimesheetEntryCodesRequestDTO requestDTO) {
        return service.create(requestDTO)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Get TimesheetEntryCodes by composite ID
     * GET /api/v1/timesheet-entry-codes/{timesheetEntriesId}/{workforceCodesId}
     */
    @GetMapping("/{timesheetEntriesId}/{workforceCodesId}")
    public ResponseEntity<TimesheetEntryCodesResponseDTO> getById(
            @PathVariable Integer timesheetEntriesId,
            @PathVariable Integer workforceCodesId) {
        return service.getById(timesheetEntriesId, workforceCodesId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntryCodes with optional filters
     * GET /api/v1/timesheet-entry-codes
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntryCodesResponseDTO>> getAll(
            @RequestParam(required = false) Integer timesheetEntriesId,
            @RequestParam(required = false) Integer workforceCodesId) {

        List<TimesheetEntryCodesResponseDTO> list;

        if (timesheetEntriesId != null) {
            list = service.getByTimesheetEntriesId(timesheetEntriesId);
        } else if (workforceCodesId != null) {
            list = service.getByWorkforceCodesId(workforceCodesId);
        } else {
            list = service.getAll();
        }

        return ResponseEntity.ok(list);
    }

    /**
     * Delete TimesheetEntryCodes by composite ID
     * DELETE /api/v1/timesheet-entry-codes/{timesheetEntriesId}/{workforceCodesId}
     */
    @DeleteMapping("/{timesheetEntriesId}/{workforceCodesId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer timesheetEntriesId,
            @PathVariable Integer workforceCodesId) {
        if (service.delete(timesheetEntriesId, workforceCodesId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete all codes for a timesheet entry
     * DELETE /api/v1/timesheet-entry-codes/entry/{timesheetEntriesId}
     */
    @DeleteMapping("/entry/{timesheetEntriesId}")
    public ResponseEntity<Void> deleteByTimesheetEntriesId(@PathVariable Integer timesheetEntriesId) {
        service.deleteByTimesheetEntriesId(timesheetEntriesId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if association exists
     * HEAD /api/v1/timesheet-entry-codes/{timesheetEntriesId}/{workforceCodesId}
     */
    @RequestMapping(value = "/{timesheetEntriesId}/{workforceCodesId}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(
            @PathVariable Integer timesheetEntriesId,
            @PathVariable Integer workforceCodesId) {
        if (service.exists(timesheetEntriesId, workforceCodesId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntryCodes
     * GET /api/v1/timesheet-entry-codes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

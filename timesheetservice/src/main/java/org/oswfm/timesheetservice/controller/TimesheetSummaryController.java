package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.TimesheetSummaryRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetSummaryService;
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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/timesheet-summary")
// @CrossOrigin(origins = "*")
public class TimesheetSummaryController {

    @Autowired
    private TimesheetSummaryService service;

    /**
     * Create a new TimesheetSummary
     * POST /api/timesheet-summary
     */
    @PostMapping
    public ResponseEntity<TimesheetSummaryResponseDTO> create(@Valid @RequestBody TimesheetSummaryRequestDTO requestDTO) {
        TimesheetSummaryResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetSummary by ID
     * GET /api/timesheet-summary/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetSummaryResponseDTO> getById(@PathVariable Integer  id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetSummarys
     * GET /api/timesheet-summary
     */
    @GetMapping
    public ResponseEntity<List<TimesheetSummaryResponseDTO>> getAll() {
        List<TimesheetSummaryResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetSummary by ID
     * PUT /api/timesheet-summary/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetSummaryResponseDTO> update(
            @PathVariable Integer  id,
            @Valid @RequestBody TimesheetSummaryRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetSummary by ID
     * DELETE /api/timesheet-summary/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer  id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetSummary exists by ID
     * HEAD /api/timesheet-summary/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer  id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetSummarys
     * GET /api/timesheet-summary/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetRemarksRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetRemarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-remarks")
@CrossOrigin(origins = "*")
public class TimesheetRemarksController {

    @Autowired
    private TimesheetRemarksService service;

    /**
     * Create a new TimesheetRemarks
     * POST /api/timesheet-remarks
     */
    @PostMapping
    public ResponseEntity<TimesheetRemarksResponseDTO> create(@Valid @RequestBody TimesheetRemarksRequestDTO requestDTO) {
        TimesheetRemarksResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetRemarks by ID
     * GET /api/timesheet-remarks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetRemarksResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetRemarkss
     * GET /api/timesheet-remarks
     */
    @GetMapping
    public ResponseEntity<List<TimesheetRemarksResponseDTO>> getAll() {
        List<TimesheetRemarksResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetRemarks by ID
     * PUT /api/timesheet-remarks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetRemarksResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetRemarksRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetRemarks by ID
     * DELETE /api/timesheet-remarks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetRemarks exists by ID
     * HEAD /api/timesheet-remarks/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetRemarkss
     * GET /api/timesheet-remarks/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

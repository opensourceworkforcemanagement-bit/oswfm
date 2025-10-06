package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetRemarksOldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-remarks-old")
@CrossOrigin(origins = "*")
public class TimesheetRemarksOldController {

    @Autowired
    private TimesheetRemarksOldService service;

    /**
     * Create a new TimesheetRemarksOld
     * POST /api/timesheet-remarks-old
     */
    @PostMapping
    public ResponseEntity<TimesheetRemarksOldResponseDTO> create(@Valid @RequestBody TimesheetRemarksOldRequestDTO requestDTO) {
        TimesheetRemarksOldResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetRemarksOld by ID
     * GET /api/timesheet-remarks-old/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetRemarksOldResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetRemarksOlds
     * GET /api/timesheet-remarks-old
     */
    @GetMapping
    public ResponseEntity<List<TimesheetRemarksOldResponseDTO>> getAll() {
        List<TimesheetRemarksOldResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetRemarksOld by ID
     * PUT /api/timesheet-remarks-old/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetRemarksOldResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetRemarksOldRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetRemarksOld by ID
     * DELETE /api/timesheet-remarks-old/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetRemarksOld exists by ID
     * HEAD /api/timesheet-remarks-old/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetRemarksOlds
     * GET /api/timesheet-remarks-old/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

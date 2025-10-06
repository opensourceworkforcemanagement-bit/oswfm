package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet")
@CrossOrigin(origins = "*")
public class TimesheetController {

    @Autowired
    private TimesheetService service;

    /**
     * Create a new Timesheet
     * POST /api/timesheet
     */
    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<TimesheetResponseDTO> create(@Valid @RequestBody TimesheetRequestDTO requestDTO) {
        TimesheetResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Timesheet by ID
     * GET /api/timesheet/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Timesheets
     * GET /api/timesheet
     */
    @GetMapping
    public ResponseEntity<List<TimesheetResponseDTO>> getAll() {
        List<TimesheetResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Timesheet by ID
     * PUT /api/timesheet/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Timesheet by ID
     * DELETE /api/timesheet/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Timesheet exists by ID
     * HEAD /api/timesheet/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Timesheets
     * GET /api/timesheet/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetEntriesInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-entries-in-out")
@CrossOrigin(origins = "*")
public class TimesheetEntriesInOutController {

    @Autowired
    private TimesheetEntriesInOutService service;

    /**
     * Create a new TimesheetEntriesInOut
     * POST /api/timesheet-entries-in-out
     */
    @PostMapping
    public ResponseEntity<TimesheetEntriesInOutResponseDTO> create(@Valid @RequestBody TimesheetEntriesInOutRequestDTO requestDTO) {
        TimesheetEntriesInOutResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetEntriesInOut by ID
     * GET /api/timesheet-entries-in-out/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetEntriesInOuts
     * GET /api/timesheet-entries-in-out
     */
    @GetMapping
    public ResponseEntity<List<TimesheetEntriesInOutResponseDTO>> getAll() {
        List<TimesheetEntriesInOutResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetEntriesInOut by ID
     * PUT /api/timesheet-entries-in-out/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetEntriesInOutResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetEntriesInOutRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetEntriesInOut by ID
     * DELETE /api/timesheet-entries-in-out/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetEntriesInOut exists by ID
     * HEAD /api/timesheet-entries-in-out/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetEntriesInOuts
     * GET /api/timesheet-entries-in-out/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

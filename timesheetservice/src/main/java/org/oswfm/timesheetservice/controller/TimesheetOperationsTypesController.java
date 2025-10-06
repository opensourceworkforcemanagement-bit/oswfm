package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetOperationsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-operations-types")
@CrossOrigin(origins = "*")
public class TimesheetOperationsTypesController {

    @Autowired
    private TimesheetOperationsTypesService service;

    /**
     * Create a new TimesheetOperationsTypes
     * POST /api/timesheet-operations-types
     */
    @PostMapping
    public ResponseEntity<TimesheetOperationsTypesResponseDTO> create(@Valid @RequestBody TimesheetOperationsTypesRequestDTO requestDTO) {
        TimesheetOperationsTypesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetOperationsTypes by ID
     * GET /api/timesheet-operations-types/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetOperationsTypesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetOperationsTypess
     * GET /api/timesheet-operations-types
     */
    @GetMapping
    public ResponseEntity<List<TimesheetOperationsTypesResponseDTO>> getAll() {
        List<TimesheetOperationsTypesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetOperationsTypes by ID
     * PUT /api/timesheet-operations-types/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetOperationsTypesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetOperationsTypesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetOperationsTypes by ID
     * DELETE /api/timesheet-operations-types/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetOperationsTypes exists by ID
     * HEAD /api/timesheet-operations-types/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetOperationsTypess
     * GET /api/timesheet-operations-types/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.WorkCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkCodesResponseDTO;
import org.oswfm.timesheetservice.service.WorkCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/work-codes")
@CrossOrigin(origins = "*")
public class WorkCodesController {

    @Autowired
    private WorkCodesService service;

    /**
     * Create a new WorkCodes
     * POST /api/work-codes
     */
    @PostMapping
    public ResponseEntity<WorkCodesResponseDTO> create(@Valid @RequestBody WorkCodesRequestDTO requestDTO) {
        WorkCodesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get WorkCodes by ID
     * GET /api/work-codes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkCodesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all WorkCodess
     * GET /api/work-codes
     */
    @GetMapping
    public ResponseEntity<List<WorkCodesResponseDTO>> getAll() {
        List<WorkCodesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update WorkCodes by ID
     * PUT /api/work-codes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkCodesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkCodesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete WorkCodes by ID
     * DELETE /api/work-codes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if WorkCodes exists by ID
     * HEAD /api/work-codes/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all WorkCodess
     * GET /api/work-codes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

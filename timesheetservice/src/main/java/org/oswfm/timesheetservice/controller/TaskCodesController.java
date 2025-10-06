package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TaskCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TaskCodesResponseDTO;
import org.oswfm.timesheetservice.service.TaskCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/task-codes")
@CrossOrigin(origins = "*")
public class TaskCodesController {

    @Autowired
    private TaskCodesService service;

    /**
     * Create a new TaskCodes
     * POST /api/task-codes
     */
    @PostMapping
    public ResponseEntity<TaskCodesResponseDTO> create(@Valid @RequestBody TaskCodesRequestDTO requestDTO) {
        TaskCodesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TaskCodes by ID
     * GET /api/task-codes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskCodesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TaskCodess
     * GET /api/task-codes
     */
    @GetMapping
    public ResponseEntity<List<TaskCodesResponseDTO>> getAll() {
        List<TaskCodesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TaskCodes by ID
     * PUT /api/task-codes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskCodesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TaskCodesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TaskCodes by ID
     * DELETE /api/task-codes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TaskCodes exists by ID
     * HEAD /api/task-codes/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TaskCodess
     * GET /api/task-codes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryResponseDTO;
import org.oswfm.employeeservice.service.EmployeesStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-status-history")
@CrossOrigin(origins = "*")
public class EmployeesStatusHistoryController {

    @Autowired
    private EmployeesStatusHistoryService service;

    /**
     * Create a new EmployeesStatusHistory
     * POST /api/employees-status-history
     */
    @PostMapping
    public ResponseEntity<EmployeesStatusHistoryResponseDTO> create(@Valid @RequestBody EmployeesStatusHistoryRequestDTO requestDTO) {
        EmployeesStatusHistoryResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesStatusHistory by ID
     * GET /api/employees-status-history/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesStatusHistoryResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesStatusHistorys
     * GET /api/employees-status-history
     */
    @GetMapping
    public ResponseEntity<List<EmployeesStatusHistoryResponseDTO>> getAll() {
        List<EmployeesStatusHistoryResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesStatusHistory by ID
     * PUT /api/employees-status-history/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesStatusHistoryResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesStatusHistoryRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesStatusHistory by ID
     * DELETE /api/employees-status-history/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesStatusHistory exists by ID
     * HEAD /api/employees-status-history/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesStatusHistorys
     * GET /api/employees-status-history/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

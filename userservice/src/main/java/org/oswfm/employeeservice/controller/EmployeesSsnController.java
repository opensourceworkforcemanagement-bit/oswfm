package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesSsnRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSsnResponseDTO;
import org.oswfm.employeeservice.service.EmployeesSsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-ssn")
@CrossOrigin(origins = "*")
public class EmployeesSsnController {

    @Autowired
    private EmployeesSsnService service;

    /**
     * Create a new EmployeesSsn
     * POST /api/employees-ssn
     */
    @PostMapping
    public ResponseEntity<EmployeesSsnResponseDTO> create(@Valid @RequestBody EmployeesSsnRequestDTO requestDTO) {
        EmployeesSsnResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesSsn by ID
     * GET /api/employees-ssn/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesSsnResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesSsns
     * GET /api/employees-ssn
     */
    @GetMapping
    public ResponseEntity<List<EmployeesSsnResponseDTO>> getAll() {
        List<EmployeesSsnResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesSsn by ID
     * PUT /api/employees-ssn/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesSsnResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesSsnRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesSsn by ID
     * DELETE /api/employees-ssn/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesSsn exists by ID
     * HEAD /api/employees-ssn/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesSsns
     * GET /api/employees-ssn/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

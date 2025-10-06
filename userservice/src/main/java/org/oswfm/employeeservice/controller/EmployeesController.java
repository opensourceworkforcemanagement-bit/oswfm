package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesResponseDTO;
import org.oswfm.employeeservice.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    /**
     * Create a new Employees
     * POST /api/employees
     */
    @PostMapping
    public ResponseEntity<EmployeesResponseDTO> create(@Valid @RequestBody EmployeesRequestDTO requestDTO) {
        EmployeesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Employees by ID
     * GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Employeess
     * GET /api/employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeesResponseDTO>> getAll() {
        List<EmployeesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Employees by ID
     * PUT /api/employees/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Employees by ID
     * DELETE /api/employees/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Employees exists by ID
     * HEAD /api/employees/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Employeess
     * GET /api/employees/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

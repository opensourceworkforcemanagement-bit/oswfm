package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.DepartmentsRequestDTO;
import org.oswfm.employeeservice.model.dto.DepartmentsResponseDTO;
import org.oswfm.employeeservice.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentsController {

    @Autowired
    private DepartmentsService service;

    /**
     * Create a new Departments
     * POST /api/departments
     */
    @PostMapping
    public ResponseEntity<DepartmentsResponseDTO> create(@Valid @RequestBody DepartmentsRequestDTO requestDTO) {
        DepartmentsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Departments by ID
     * GET /api/departments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Departmentss
     * GET /api/departments
     */
    @GetMapping
    public ResponseEntity<List<DepartmentsResponseDTO>> getAll() {
        List<DepartmentsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Departments by ID
     * PUT /api/departments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody DepartmentsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Departments by ID
     * DELETE /api/departments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Departments exists by ID
     * HEAD /api/departments/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Departmentss
     * GET /api/departments/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

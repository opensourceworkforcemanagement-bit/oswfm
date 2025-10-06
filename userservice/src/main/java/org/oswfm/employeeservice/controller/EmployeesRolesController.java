package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesRolesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesRolesResponseDTO;
import org.oswfm.employeeservice.service.EmployeesRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-roles")
@CrossOrigin(origins = "*")
public class EmployeesRolesController {

    @Autowired
    private EmployeesRolesService service;

    /**
     * Create a new EmployeesRoles
     * POST /api/employees-roles
     */
    @PostMapping
    public ResponseEntity<EmployeesRolesResponseDTO> create(@Valid @RequestBody EmployeesRolesRequestDTO requestDTO) {
        EmployeesRolesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesRoles by ID
     * GET /api/employees-roles/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesRolesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesRoless
     * GET /api/employees-roles
     */
    @GetMapping
    public ResponseEntity<List<EmployeesRolesResponseDTO>> getAll() {
        List<EmployeesRolesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesRoles by ID
     * PUT /api/employees-roles/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesRolesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesRolesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesRoles by ID
     * DELETE /api/employees-roles/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesRoles exists by ID
     * HEAD /api/employees-roles/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesRoless
     * GET /api/employees-roles/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

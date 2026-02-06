package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeeUserRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeUserResponseDTO;
import org.oswfm.employeeservice.service.EmployeeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-user")
public class EmployeeUserController {

    @Autowired
    private EmployeeUserService service;

    /**
     * Create a new EmployeeUser
     * POST /api/v1/employee-user
     */
    @PostMapping
    public ResponseEntity<EmployeeUserResponseDTO> create(@Valid @RequestBody EmployeeUserRequestDTO requestDTO) {
        EmployeeUserResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeeUser by ID
     * GET /api/v1/employee-user/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeUserResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeeUsers
     * GET /api/v1/employee-user
     */
    @GetMapping
    public ResponseEntity<List<EmployeeUserResponseDTO>> getAll() {
        List<EmployeeUserResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Get EmployeeUsers by employee ID
     * GET /api/v1/employee-user/employee/{employeeId}
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeUserResponseDTO>> getByEmployeeId(@PathVariable Integer employeeId) {
        List<EmployeeUserResponseDTO> list = service.getByEmployeeId(employeeId);
        return ResponseEntity.ok(list);
    }

    /**
     * Get EmployeeUsers by user ID
     * GET /api/v1/employee-user/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EmployeeUserResponseDTO>> getByUserId(@PathVariable Integer userId) {
        List<EmployeeUserResponseDTO> list = service.getByUserId(userId);
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeeUser by ID
     * PUT /api/v1/employee-user/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeUserResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeUserRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeeUser by ID
     * DELETE /api/v1/employee-user/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeeUser exists by ID
     * HEAD /api/v1/employee-user/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeeUsers
     * GET /api/v1/employee-user/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

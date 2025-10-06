package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeeAddressRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeAddressResponseDTO;
import org.oswfm.employeeservice.service.EmployeeAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employee-address")
@CrossOrigin(origins = "*")
public class EmployeeAddressController {

    @Autowired
    private EmployeeAddressService service;

    /**
     * Create a new EmployeeAddress
     * POST /api/employee-address
     */
    @PostMapping
    public ResponseEntity<EmployeeAddressResponseDTO> create(@Valid @RequestBody EmployeeAddressRequestDTO requestDTO) {
        EmployeeAddressResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeeAddress by ID
     * GET /api/employee-address/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAddressResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeeAddresss
     * GET /api/employee-address
     */
    @GetMapping
    public ResponseEntity<List<EmployeeAddressResponseDTO>> getAll() {
        List<EmployeeAddressResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeeAddress by ID
     * PUT /api/employee-address/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeAddressResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeAddressRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeeAddress by ID
     * DELETE /api/employee-address/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeeAddress exists by ID
     * HEAD /api/employee-address/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeeAddresss
     * GET /api/employee-address/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

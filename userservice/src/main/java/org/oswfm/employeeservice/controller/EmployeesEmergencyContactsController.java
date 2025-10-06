package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsResponseDTO;
import org.oswfm.employeeservice.service.EmployeesEmergencyContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-emergency-contacts")
@CrossOrigin(origins = "*")
public class EmployeesEmergencyContactsController {

    @Autowired
    private EmployeesEmergencyContactsService service;

    /**
     * Create a new EmployeesEmergencyContacts
     * POST /api/employees-emergency-contacts
     */
    @PostMapping
    public ResponseEntity<EmployeesEmergencyContactsResponseDTO> create(@Valid @RequestBody EmployeesEmergencyContactsRequestDTO requestDTO) {
        EmployeesEmergencyContactsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesEmergencyContacts by ID
     * GET /api/employees-emergency-contacts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesEmergencyContactss
     * GET /api/employees-emergency-contacts
     */
    @GetMapping
    public ResponseEntity<List<EmployeesEmergencyContactsResponseDTO>> getAll() {
        List<EmployeesEmergencyContactsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesEmergencyContacts by ID
     * PUT /api/employees-emergency-contacts/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesEmergencyContactsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesEmergencyContacts by ID
     * DELETE /api/employees-emergency-contacts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesEmergencyContacts exists by ID
     * HEAD /api/employees-emergency-contacts/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesEmergencyContactss
     * GET /api/employees-emergency-contacts/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

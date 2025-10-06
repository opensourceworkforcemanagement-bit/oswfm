package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsResponseDTO;
import org.oswfm.employeeservice.service.EmployeesEmergencyContactEmailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-emergency-contact-emails")
@CrossOrigin(origins = "*")
public class EmployeesEmergencyContactEmailsController {

    @Autowired
    private EmployeesEmergencyContactEmailsService service;

    /**
     * Create a new EmployeesEmergencyContactEmails
     * POST /api/employees-emergency-contact-emails
     */
    @PostMapping
    public ResponseEntity<EmployeesEmergencyContactEmailsResponseDTO> create(@Valid @RequestBody EmployeesEmergencyContactEmailsRequestDTO requestDTO) {
        EmployeesEmergencyContactEmailsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesEmergencyContactEmails by ID
     * GET /api/employees-emergency-contact-emails/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactEmailsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesEmergencyContactEmailss
     * GET /api/employees-emergency-contact-emails
     */
    @GetMapping
    public ResponseEntity<List<EmployeesEmergencyContactEmailsResponseDTO>> getAll() {
        List<EmployeesEmergencyContactEmailsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesEmergencyContactEmails by ID
     * PUT /api/employees-emergency-contact-emails/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactEmailsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesEmergencyContactEmailsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesEmergencyContactEmails by ID
     * DELETE /api/employees-emergency-contact-emails/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesEmergencyContactEmails exists by ID
     * HEAD /api/employees-emergency-contact-emails/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesEmergencyContactEmailss
     * GET /api/employees-emergency-contact-emails/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

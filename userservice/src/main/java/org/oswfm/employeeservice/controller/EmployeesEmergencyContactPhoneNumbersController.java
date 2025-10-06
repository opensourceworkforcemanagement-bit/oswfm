package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersResponseDTO;
import org.oswfm.employeeservice.service.EmployeesEmergencyContactPhoneNumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/employees-emergency-contact-phone-numbers")
@CrossOrigin(origins = "*")
public class EmployeesEmergencyContactPhoneNumbersController {

    @Autowired
    private EmployeesEmergencyContactPhoneNumbersService service;

    /**
     * Create a new EmployeesEmergencyContactPhoneNumbers
     * POST /api/employees-emergency-contact-phone-numbers
     */
    @PostMapping
    public ResponseEntity<EmployeesEmergencyContactPhoneNumbersResponseDTO> create(@Valid @RequestBody EmployeesEmergencyContactPhoneNumbersRequestDTO requestDTO) {
        EmployeesEmergencyContactPhoneNumbersResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmployeesEmergencyContactPhoneNumbers by ID
     * GET /api/employees-emergency-contact-phone-numbers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactPhoneNumbersResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmployeesEmergencyContactPhoneNumberss
     * GET /api/employees-emergency-contact-phone-numbers
     */
    @GetMapping
    public ResponseEntity<List<EmployeesEmergencyContactPhoneNumbersResponseDTO>> getAll() {
        List<EmployeesEmergencyContactPhoneNumbersResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmployeesEmergencyContactPhoneNumbers by ID
     * PUT /api/employees-emergency-contact-phone-numbers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeesEmergencyContactPhoneNumbersResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeesEmergencyContactPhoneNumbersRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmployeesEmergencyContactPhoneNumbers by ID
     * DELETE /api/employees-emergency-contact-phone-numbers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmployeesEmergencyContactPhoneNumbers exists by ID
     * HEAD /api/employees-emergency-contact-phone-numbers/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmployeesEmergencyContactPhoneNumberss
     * GET /api/employees-emergency-contact-phone-numbers/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

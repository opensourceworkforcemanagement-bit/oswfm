package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.EmailAddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmailAddressesResponseDTO;
import org.oswfm.employeeservice.service.EmailAddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/email-addresses")
@CrossOrigin(origins = "*")
public class EmailAddressesController {

    @Autowired
    private EmailAddressesService service;

    /**
     * Create a new EmailAddresses
     * POST /api/email-addresses
     */
    @PostMapping
    public ResponseEntity<EmailAddressesResponseDTO> create(@Valid @RequestBody EmailAddressesRequestDTO requestDTO) {
        EmailAddressesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get EmailAddresses by ID
     * GET /api/email-addresses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmailAddressesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all EmailAddressess
     * GET /api/email-addresses
     */
    @GetMapping
    public ResponseEntity<List<EmailAddressesResponseDTO>> getAll() {
        List<EmailAddressesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update EmailAddresses by ID
     * PUT /api/email-addresses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmailAddressesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmailAddressesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete EmailAddresses by ID
     * DELETE /api/email-addresses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if EmailAddresses exists by ID
     * HEAD /api/email-addresses/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all EmailAddressess
     * GET /api/email-addresses/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

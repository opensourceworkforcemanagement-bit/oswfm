package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.PhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.PhoneNumbersResponseDTO;
import org.oswfm.employeeservice.service.PhoneNumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/phone-numbers")
@CrossOrigin(origins = "*")
public class PhoneNumbersController {

    @Autowired
    private PhoneNumbersService service;

    /**
     * Create a new PhoneNumbers
     * POST /api/phone-numbers
     */
    @PostMapping
    public ResponseEntity<PhoneNumbersResponseDTO> create(@Valid @RequestBody PhoneNumbersRequestDTO requestDTO) {
        PhoneNumbersResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get PhoneNumbers by ID
     * GET /api/phone-numbers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumbersResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all PhoneNumberss
     * GET /api/phone-numbers
     */
    @GetMapping
    public ResponseEntity<List<PhoneNumbersResponseDTO>> getAll() {
        List<PhoneNumbersResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update PhoneNumbers by ID
     * PUT /api/phone-numbers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumbersResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PhoneNumbersRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete PhoneNumbers by ID
     * DELETE /api/phone-numbers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if PhoneNumbers exists by ID
     * HEAD /api/phone-numbers/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all PhoneNumberss
     * GET /api/phone-numbers/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.employeeservice.controller;

import org.oswfm.employeeservice.model.dto.AddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.AddressesResponseDTO;
import org.oswfm.employeeservice.service.AddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*")
public class AddressesController {

    @Autowired
    private AddressesService service;

    /**
     * Create a new Addresses
     * POST /api/addresses
     */
    @PostMapping
    public ResponseEntity<AddressesResponseDTO> create(@Valid @RequestBody AddressesRequestDTO requestDTO) {
        AddressesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get Addresses by ID
     * GET /api/addresses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all Addressess
     * GET /api/addresses
     */
    @GetMapping
    public ResponseEntity<List<AddressesResponseDTO>> getAll() {
        List<AddressesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update Addresses by ID
     * PUT /api/addresses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AddressesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete Addresses by ID
     * DELETE /api/addresses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if Addresses exists by ID
     * HEAD /api/addresses/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all Addressess
     * GET /api/addresses/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

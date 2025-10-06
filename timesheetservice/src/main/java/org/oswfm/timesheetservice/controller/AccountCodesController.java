package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.AccountCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.AccountCodesResponseDTO;
import org.oswfm.timesheetservice.service.AccountCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/account-codes")
@CrossOrigin(origins = "*")
public class AccountCodesController {

    @Autowired
    private AccountCodesService service;

    /**
     * Create a new AccountCodes
     * POST /api/account-codes
     */
    @PostMapping
    public ResponseEntity<AccountCodesResponseDTO> create(@Valid @RequestBody AccountCodesRequestDTO requestDTO) {
        AccountCodesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get AccountCodes by ID
     * GET /api/account-codes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountCodesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all AccountCodess
     * GET /api/account-codes
     */
    @GetMapping
    public ResponseEntity<List<AccountCodesResponseDTO>> getAll() {
        List<AccountCodesResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update AccountCodes by ID
     * PUT /api/account-codes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountCodesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AccountCodesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete AccountCodes by ID
     * DELETE /api/account-codes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if AccountCodes exists by ID
     * HEAD /api/account-codes/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all AccountCodess
     * GET /api/account-codes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

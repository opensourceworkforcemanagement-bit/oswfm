package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.PayPeriodsRequestDTO;
import org.oswfm.timesheetservice.model.dto.PayPeriodsResponseDTO;
import org.oswfm.timesheetservice.service.PayPeriodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/pay-periods")
@CrossOrigin(origins = "*")
public class PayPeriodsController {

    @Autowired
    private PayPeriodsService service;

    /**
     * Create a new PayPeriods
     * POST /api/pay-periods
     */
    @PostMapping
    public ResponseEntity<PayPeriodsResponseDTO> create(@Valid @RequestBody PayPeriodsRequestDTO requestDTO) {
        PayPeriodsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get PayPeriods by ID
     * GET /api/pay-periods/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PayPeriodsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all PayPeriodss
     * GET /api/pay-periods
     */
    @GetMapping
    public ResponseEntity<List<PayPeriodsResponseDTO>> getAll() {
        List<PayPeriodsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update PayPeriods by ID
     * PUT /api/pay-periods/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PayPeriodsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PayPeriodsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete PayPeriods by ID
     * DELETE /api/pay-periods/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if PayPeriods exists by ID
     * HEAD /api/pay-periods/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all PayPeriodss
     * GET /api/pay-periods/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

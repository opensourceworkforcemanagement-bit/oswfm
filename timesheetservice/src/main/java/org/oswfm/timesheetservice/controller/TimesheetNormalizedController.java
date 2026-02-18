package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetNormalizedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/timesheets-normalized")
public class TimesheetNormalizedController {

    @Autowired
    private TimesheetNormalizedService service;

    /**
     * Create a new TimesheetNormalized
     * POST /api/v1/timesheets-normalized/create
     */
    @PostMapping("/create")
    public ResponseEntity<TimesheetNormalizedResponseDTO> create(@Valid @RequestBody TimesheetNormalizedRequestDTO requestDTO) {
        TimesheetNormalizedResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetNormalized by ID (includes entries)
     * GET /api/v1/timesheets-normalized/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetNormalizedResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetNormalized with optional filters
     * GET /api/v1/timesheets-normalized
     */
    @GetMapping
    public ResponseEntity<List<TimesheetNormalizedResponseDTO>> getAll(
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) Integer payPeriodId,
            @RequestParam(required = false) Integer status) {

        List<TimesheetNormalizedResponseDTO> list;

        if (employeeId != null && payPeriodId != null) {
            list = service.getByEmployeeIdAndPayPeriodId(employeeId, payPeriodId);
        } else if (employeeId != null && status != null) {
            list = service.getByEmployeeIdAndStatus(employeeId, status);
        } else if (employeeId != null) {
            list = service.getByEmployeeId(employeeId);
        } else if (payPeriodId != null) {
            list = service.getByPayPeriodId(payPeriodId);
        } else if (status != null) {
            list = service.getByStatus(status);
        } else {
            list = service.getAll();
        }

        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetNormalized by ID
     * PUT /api/v1/timesheets-normalized/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetNormalizedResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetNormalizedRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update TimesheetNormalized status only
     * PATCH /api/v1/timesheets-normalized/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TimesheetNormalizedResponseDTO> updateStatus(
            @PathVariable Integer id,
            @RequestParam Integer status) {
        return service.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetNormalized by ID
     * DELETE /api/v1/timesheets-normalized/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetNormalized exists by ID
     * HEAD /api/v1/timesheets-normalized/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetNormalized
     * GET /api/v1/timesheets-normalized/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

package org.oswfm.timesheetservice.controller;

import java.util.List;

import org.oswfm.timesheetservice.model.dto.WorkforceCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkforceCodesResponseDTO;
import org.oswfm.timesheetservice.service.WorkforceCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/workforce-codes")
public class WorkforceCodesController {

    @Autowired
    private WorkforceCodesService service;

    /**
     * Create a new WorkforceCodes
     * POST /api/v1/workforce-codes/create
     */
    @PostMapping("/create")
    public ResponseEntity<WorkforceCodesResponseDTO> create(@Valid @RequestBody WorkforceCodesRequestDTO requestDTO) {
        WorkforceCodesResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get WorkforceCodes by ID
     * GET /api/v1/workforce-codes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkforceCodesResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all WorkforceCodes
     * GET /api/v1/workforce-codes
     */
    @GetMapping
    public ResponseEntity<List<WorkforceCodesResponseDTO>> getAll(
            @RequestParam(required = false) Integer codeTypeId,
            @RequestParam(required = false) Integer status) {

        List<WorkforceCodesResponseDTO> list;

        if (codeTypeId != null && status != null) {
            list = service.getByCodeTypeIdAndStatus(codeTypeId, status);
        } else if (codeTypeId != null) {
            list = service.getByCodeTypeId(codeTypeId);
        } else if (status != null) {
            list = service.getByStatus(status);
        } else {
            list = service.getAll();
        }

        return ResponseEntity.ok(list);
    }

    /**
     * Update WorkforceCodes by ID
     * PUT /api/v1/workforce-codes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkforceCodesResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkforceCodesRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete WorkforceCodes by ID
     * DELETE /api/v1/workforce-codes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if WorkforceCodes exists by ID
     * HEAD /api/v1/workforce-codes/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all WorkforceCodes
     * GET /api/v1/workforce-codes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

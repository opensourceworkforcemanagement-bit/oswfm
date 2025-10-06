package org.oswfm.timesheetservice.controller;

import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsResponseDTO;
import org.oswfm.timesheetservice.service.TimesheetApprovalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/timesheet-approvals")
@CrossOrigin(origins = "*")
public class TimesheetApprovalsController {

    @Autowired
    private TimesheetApprovalsService service;

    /**
     * Create a new TimesheetApprovals
     * POST /api/timesheet-approvals
     */
    @PostMapping
    public ResponseEntity<TimesheetApprovalsResponseDTO> create(@Valid @RequestBody TimesheetApprovalsRequestDTO requestDTO) {
        TimesheetApprovalsResponseDTO created = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get TimesheetApprovals by ID
     * GET /api/timesheet-approvals/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimesheetApprovalsResponseDTO> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all TimesheetApprovalss
     * GET /api/timesheet-approvals
     */
    @GetMapping
    public ResponseEntity<List<TimesheetApprovalsResponseDTO>> getAll() {
        List<TimesheetApprovalsResponseDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Update TimesheetApprovals by ID
     * PUT /api/timesheet-approvals/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimesheetApprovalsResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody TimesheetApprovalsRequestDTO requestDTO) {
        return service.update(id, requestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete TimesheetApprovals by ID
     * DELETE /api/timesheet-approvals/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if TimesheetApprovals exists by ID
     * HEAD /api/timesheet-approvals/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exists(@PathVariable Integer id) {
        if (service.exists(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get count of all TimesheetApprovalss
     * GET /api/timesheet-approvals/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }
}

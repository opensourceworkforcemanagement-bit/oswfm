package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.OperationDTO;
import org.oswfm.accesscontrolservice.service.OperationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
@Tag(name = "Operations", description = "Operation management APIs for access control")
public class OperationController {

    @Autowired
    private final OperationService operationService;

    @GetMapping
    @Operation(summary = "Get all operations")
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        return ResponseEntity.ok(operationService.getAllOperations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get operation by ID")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable Integer id) {
        return ResponseEntity.ok(operationService.getOperationById(id));
    }

    @GetMapping("/name/{operationName}")
    @Operation(summary = "Get operation by name")
    public ResponseEntity<OperationDTO> getOperationByName(@PathVariable String operationName) {
        return ResponseEntity.ok(operationService.getOperationByName(operationName));
    }

    @PostMapping
    @Operation(summary = "Create a new operation")
    public ResponseEntity<OperationDTO> createOperation(@Valid @RequestBody OperationDTO operationDTO) {
        OperationDTO createdOperation = operationService.createOperation(operationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing operation")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable Integer id, @Valid @RequestBody OperationDTO operationDTO) {
        return ResponseEntity.ok(operationService.updateOperation(id, operationDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an operation")
    public ResponseEntity<Void> deleteOperation(@PathVariable Integer id) {
        operationService.deleteOperation(id);
        return ResponseEntity.noContent().build();
    }
}

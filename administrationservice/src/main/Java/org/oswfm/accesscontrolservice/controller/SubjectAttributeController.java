package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.SubjectAttributeDTO;
import org.oswfm.accesscontrolservice.service.SubjectAttributeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/subject-attributes")
@RequiredArgsConstructor
@Tag(name = "Subject Attributes", description = "Subject attribute management APIs")
public class SubjectAttributeController {
    
    @Autowired
    private final SubjectAttributeService subjectAttributeService;
    
    @GetMapping
    @Operation(summary = "Get all subject attributes")
    public ResponseEntity<List<SubjectAttributeDTO>> getAllSubjectAttributes() {
        return ResponseEntity.ok(subjectAttributeService.getAllSubjectAttributes());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get subject attribute by ID")
    public ResponseEntity<SubjectAttributeDTO> getSubjectAttributeById(@PathVariable  Integer id) {
        return ResponseEntity.ok(subjectAttributeService.getSubjectAttributeById(id));
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get subject attributes by user ID")
    public ResponseEntity<List<SubjectAttributeDTO>> getSubjectAttributesByUserId(@PathVariable  Integer userId) {
        return ResponseEntity.ok(subjectAttributeService.getSubjectAttributesByUserId(userId));
    }
    
    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active subject attributes by user ID")
    public ResponseEntity<List<SubjectAttributeDTO>> getActiveSubjectAttributesByUserId(@PathVariable  Integer userId) {
        return ResponseEntity.ok(subjectAttributeService.getActiveSubjectAttributesByUserId(userId));
    }
    
    @PostMapping
    @Operation(summary = "Create a new subject attribute")
    public ResponseEntity<SubjectAttributeDTO> createSubjectAttribute(@Valid @RequestBody SubjectAttributeDTO subjectAttributeDTO) {
        SubjectAttributeDTO createdSubjectAttribute = subjectAttributeService.createSubjectAttribute(subjectAttributeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubjectAttribute);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subject attribute")
    public ResponseEntity<SubjectAttributeDTO> updateSubjectAttribute(@PathVariable  Integer id, @Valid @RequestBody SubjectAttributeDTO subjectAttributeDTO) {
        return ResponseEntity.ok(subjectAttributeService.updateSubjectAttribute(id, subjectAttributeDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject attribute")
    public ResponseEntity<Void> deleteSubjectAttribute(@PathVariable  Integer id) {
        subjectAttributeService.deleteSubjectAttribute(id);
        return ResponseEntity.noContent().build();
    }
}

package org.oswfm.accesscontrolservice.controller;

import org.oswfm.accesscontrolservice.dto.GroupSubjectAttributeDTO;
import org.oswfm.accesscontrolservice.dto.SubjectAttributeDTO;
import org.oswfm.accesscontrolservice.dto.UserSubjectAttributeDTO;
import org.oswfm.accesscontrolservice.service.SubjectAttributeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject-attributes")
@RequiredArgsConstructor
@Tag(name = "Subject Attributes", description = "Subject attribute management APIs")
public class SubjectAttributeController {

    private final SubjectAttributeService subjectAttributeService;

    @GetMapping
    @Operation(summary = "Get all subject attributes")
    public ResponseEntity<List<SubjectAttributeDTO>> getAllSubjectAttributes() {
        return ResponseEntity.ok(subjectAttributeService.getAllSubjectAttributes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subject attribute by ID")
    public ResponseEntity<SubjectAttributeDTO> getSubjectAttributeById(@PathVariable Integer id) {
        return ResponseEntity.ok(subjectAttributeService.getSubjectAttributeById(id));
    }

    @GetMapping("/user/{userId}/resolved")
    @Operation(summary = "Get all resolved attributes for a user (direct + group assignments)")
    public ResponseEntity<List<SubjectAttributeDTO>> getResolvedAttributesByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(subjectAttributeService.getResolvedAttributesByUserId(userId));
    }

    @GetMapping("/by-attribute-name/{attributeName}")
    @Operation(summary = "Get subject attributes by attribute definition name")
    public ResponseEntity<List<SubjectAttributeDTO>> getSubjectAttributesByAttributeName(
            @PathVariable String attributeName) {
        return ResponseEntity.ok(subjectAttributeService.getSubjectAttributesByAttributeName(attributeName));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get attributes assigned to a group")
    public ResponseEntity<List<SubjectAttributeDTO>> getAttributesByGroupId(@PathVariable Integer groupId) {
        return ResponseEntity.ok(subjectAttributeService.getAttributesByGroupId(groupId));
    }

    @PostMapping
    @Operation(summary = "Create a new subject attribute")
    public ResponseEntity<SubjectAttributeDTO> createSubjectAttribute(@Valid @RequestBody SubjectAttributeDTO subjectAttributeDTO) {
        SubjectAttributeDTO createdSubjectAttribute = subjectAttributeService.createSubjectAttribute(subjectAttributeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubjectAttribute);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subject attribute")
    public ResponseEntity<SubjectAttributeDTO> updateSubjectAttribute(@PathVariable Integer id, @Valid @RequestBody SubjectAttributeDTO subjectAttributeDTO) {
        return ResponseEntity.ok(subjectAttributeService.updateSubjectAttribute(id, subjectAttributeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject attribute")
    public ResponseEntity<Void> deleteSubjectAttribute(@PathVariable Integer id) {
        subjectAttributeService.deleteSubjectAttribute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign/user")
    @Operation(summary = "Assign a subject attribute directly to a user")
    public ResponseEntity<UserSubjectAttributeDTO> assignAttributeToUser(@Valid @RequestBody UserSubjectAttributeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectAttributeService.assignAttributeToUser(dto));
    }

    @DeleteMapping("/assign/user/{userId}/{subjectAttrId}")
    @Operation(summary = "Remove a direct attribute assignment from a user")
    public ResponseEntity<Void> removeAttributeFromUser(@PathVariable Integer userId, @PathVariable Integer subjectAttrId) {
        subjectAttributeService.removeAttributeFromUser(userId, subjectAttrId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign/group")
    @Operation(summary = "Assign a subject attribute to a group")
    public ResponseEntity<GroupSubjectAttributeDTO> assignAttributeToGroup(@Valid @RequestBody GroupSubjectAttributeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectAttributeService.assignAttributeToGroup(dto));
    }

    @DeleteMapping("/assign/group/{groupId}/{subjectAttrId}")
    @Operation(summary = "Remove an attribute assignment from a group")
    public ResponseEntity<Void> removeAttributeFromGroup(@PathVariable Integer groupId, @PathVariable Integer subjectAttrId) {
        subjectAttributeService.removeAttributeFromGroup(groupId, subjectAttrId);
        return ResponseEntity.noContent().build();
    }
}

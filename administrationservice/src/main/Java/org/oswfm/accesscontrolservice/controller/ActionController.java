package org.oswfm.accesscontrolservice.controller;

import java.util.List;

import org.oswfm.accesscontrolservice.dto.ActionDTO;
import org.oswfm.accesscontrolservice.service.ActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/v1/operations")
@RequiredArgsConstructor
@Tag(name = "Actions", description = "Action management APIs")
public class ActionController {
    
    private final ActionService actionService;
    
    @GetMapping
    @Operation(summary = "Get all actions")
    public ResponseEntity<List<ActionDTO>> getAllActions() {
        return ResponseEntity.ok(actionService.getAllActions());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get action by ID")
    public ResponseEntity<ActionDTO> getActionById(@PathVariable  Integer id) {
        return ResponseEntity.ok(actionService.getActionById(id));
    }
    
    @GetMapping("/name/{actionName}")
    @Operation(summary = "Get action by name")
    public ResponseEntity<ActionDTO> getActionByName(@PathVariable String actionName) {
        return ResponseEntity.ok(actionService.getActionByName(actionName));
    }
    
    @PostMapping
    @Operation(summary = "Create a new action")
    public ResponseEntity<ActionDTO> createAction(@Valid @RequestBody ActionDTO actionDTO) {
        ActionDTO createdAction = actionService.createAction(actionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAction);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing action")
    public ResponseEntity<ActionDTO> updateAction(@PathVariable  Integer id, @Valid @RequestBody ActionDTO actionDTO) {
        return ResponseEntity.ok(actionService.updateAction(id, actionDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an action")
    public ResponseEntity<Void> deleteAction(@PathVariable  Integer id) {
        actionService.deleteAction(id);
        return ResponseEntity.noContent().build();
    }
}

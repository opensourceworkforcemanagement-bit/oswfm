package org.oswfm.accesscontrolservice.controller;

import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.dto.UserDTO;
import org.oswfm.accesscontrolservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/users")
// @RequiredArgsConstructor
// @Tag(name = "Users", description = "User management APIs")
public class UserController {
    
    // private UserService userService;
    
    // @GetMapping
    // @Operation(summary = "Get all users")
    // public ResponseEntity<List<UserDTO>> getAllUsers() {
    //     return ResponseEntity.ok(userService.getAllUsers());
    // }
    
    // @GetMapping("/{id}")
    // @Operation(summary = "Get user by ID")
    // public ResponseEntity<UserDTO> getUserById(@PathVariable  Integer id) {
    //     return ResponseEntity.ok(userService.getUserById(id));
    // }
    
    // @GetMapping("/username/{username}")
    // @Operation(summary = "Get user by username")
    // public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
    //     return ResponseEntity.ok(userService.getUserByUsername(username));
    // }
    
    // @GetMapping("/active")
    // @Operation(summary = "Get all active users")
    // public ResponseEntity<List<UserDTO>> getActiveUsers() {
    //     return ResponseEntity.ok(userService.getActiveUsers());
    // }
    
    // @PostMapping
    // @Operation(summary = "Create a new user")
    // public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
    //     UserDTO createdUser = userService.createUser(userDTO);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    // }
    
    // @PutMapping("/{id}")
    // @Operation(summary = "Update an existing user")
    // public ResponseEntity<UserDTO> updateUser(@PathVariable  Integer id, @Valid @RequestBody UserDTO userDTO) {
    //     return ResponseEntity.ok(userService.updateUser(id, userDTO));
    // }
    
    // @DeleteMapping("/{id}")
    // @Operation(summary = "Delete a user")
    // public ResponseEntity<Void> deleteUser(@PathVariable  Integer id) {
    //     userService.deleteUser(id);
    //     return ResponseEntity.noContent().build();
    // }
}

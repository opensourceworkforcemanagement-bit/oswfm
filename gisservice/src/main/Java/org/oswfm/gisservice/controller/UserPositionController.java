package org.oswfm.gisservice.controller;

import java.util.List;

import org.oswfm.gisservice.dto.UserPositionDTO;
import org.oswfm.gisservice.service.UserPositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-positions")
@RequiredArgsConstructor
@Tag(name = "User Positions", description = "User position tracking APIs")
public class UserPositionController {

    private final UserPositionService userPositionService;

    @GetMapping
    @Operation(summary = "Get all user positions")
    public ResponseEntity<List<UserPositionDTO>> getAllPositions() {
        return ResponseEntity.ok(userPositionService.getAllPositions());
    }

    @GetMapping("/latest")
    @Operation(summary = "Get the latest position for each user")
    public ResponseEntity<List<UserPositionDTO>> getLatestPositionPerUser() {
        return ResponseEntity.ok(userPositionService.getLatestPositionPerUser());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user position by ID")
    public ResponseEntity<UserPositionDTO> getPositionById(@PathVariable Integer id) {
        return ResponseEntity.ok(userPositionService.getPositionById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get position by user ID")
    public ResponseEntity<UserPositionDTO> getPositionByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userPositionService.getPositionByUserId(userId));
    }

    @GetMapping("/nearby")
    @Operation(summary = "Find users within a given distance (meters) from a point")
    public ResponseEntity<List<UserPositionDTO>> findUsersNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double distanceMeters) {
        return ResponseEntity.ok(userPositionService.findUsersWithinDistance(lat, lon, distanceMeters));
    }

    @PostMapping
    @Operation(summary = "Create a new user position")
    public ResponseEntity<UserPositionDTO> createPosition(@Valid @RequestBody UserPositionDTO dto) {
        UserPositionDTO created = userPositionService.createPosition(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user position")
    public ResponseEntity<UserPositionDTO> updatePosition(@PathVariable Integer id, @Valid @RequestBody UserPositionDTO dto) {
        return ResponseEntity.ok(userPositionService.updatePosition(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user position")
    public ResponseEntity<Void> deletePosition(@PathVariable Integer id) {
        userPositionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}

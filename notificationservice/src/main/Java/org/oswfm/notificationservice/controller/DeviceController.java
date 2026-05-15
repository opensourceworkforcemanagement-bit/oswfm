package org.oswfm.notificationservice.controller;

import org.oswfm.notificationservice.model.DeviceRegistrationRequest;
import org.oswfm.notificationservice.service.DeviceTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceTokenStore deviceTokenStore;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody DeviceRegistrationRequest request) {
        if (request.getToken() == null || request.getToken().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        deviceTokenStore.register(request.getToken(), request.getPlatform() != null ? request.getPlatform() : "unknown");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.ok(deviceTokenStore.count());
    }
}

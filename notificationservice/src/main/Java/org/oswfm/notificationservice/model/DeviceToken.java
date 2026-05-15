package org.oswfm.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToken {
    private String token;
    private String platform;
    private Instant registeredAt;
}

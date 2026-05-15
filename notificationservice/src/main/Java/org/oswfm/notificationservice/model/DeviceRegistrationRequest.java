package org.oswfm.notificationservice.model;

import lombok.Data;

@Data
public class DeviceRegistrationRequest {
    private String token;
    private String platform;
}

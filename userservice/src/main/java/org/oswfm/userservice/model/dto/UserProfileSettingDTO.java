package org.oswfm.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSettingDTO {

    private Integer profileSettingId;
    private Integer userId;
    private String settingKey;
    private String settingValue;
}

package org.oswfm.userservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_profile_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_setting_id")
    private Integer profileSettingId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "setting_key", nullable = false, length = 100)
    private String settingKey;

    @Column(name = "setting_value", nullable = false, length = 255)
    private String settingValue;
}

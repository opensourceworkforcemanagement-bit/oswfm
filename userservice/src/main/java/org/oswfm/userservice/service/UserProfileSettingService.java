package org.oswfm.userservice.service;

import org.oswfm.userservice.model.dto.UserProfileSettingDTO;

import java.util.List;

public interface UserProfileSettingService {

    List<UserProfileSettingDTO> getSettingsByUserId(Integer userId);

    UserProfileSettingDTO saveSetting(UserProfileSettingDTO dto);

    void deleteSetting(Integer profileSettingId);

    void deleteSettingByUserIdAndKey(Integer userId, String settingKey);
}

package org.oswfm.userservice.service;

import java.util.List;

import org.oswfm.userservice.model.dto.UserProfileSettingDTO;

public interface UserProfileSettingService {

    List<UserProfileSettingDTO> getSettingsByUserId(Integer userId);

    UserProfileSettingDTO saveSetting(UserProfileSettingDTO dto);

    void deleteSetting(Integer profileSettingId);

    void deleteSettingByUserIdAndKey(Integer userId, String settingKey);

    public void deleteSettingsByUserId(Integer id);
}

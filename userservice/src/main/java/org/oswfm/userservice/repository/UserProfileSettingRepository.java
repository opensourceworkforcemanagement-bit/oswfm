package org.oswfm.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.userservice.model.entity.UserProfileSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileSettingRepository extends JpaRepository<UserProfileSetting, Integer> {

    List<UserProfileSetting> findByUserId(Integer userId);

    Optional<UserProfileSetting> findByUserIdAndSettingKey(Integer userId, String settingKey);

    void deleteByUserIdAndSettingKey(Integer userId, String settingKey);

    public void deleteByUserId(Integer userId);
}

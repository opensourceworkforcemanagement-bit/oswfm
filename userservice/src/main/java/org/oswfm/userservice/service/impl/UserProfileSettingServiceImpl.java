package org.oswfm.userservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.oswfm.userservice.model.dto.UserProfileSettingDTO;
import org.oswfm.userservice.model.entity.UserProfileSetting;
import org.oswfm.userservice.repository.UserProfileSettingRepository;
import org.oswfm.userservice.service.UserProfileSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileSettingServiceImpl implements UserProfileSettingService {

    private final UserProfileSettingRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<UserProfileSettingDTO> getSettingsByUserId(Integer userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserProfileSettingDTO saveSetting(UserProfileSettingDTO dto) {
        Optional<UserProfileSetting> existing = repository
                .findByUserIdAndSettingKey(dto.getUserId(), dto.getSettingKey());

        UserProfileSetting entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setSettingValue(dto.getSettingValue());
        } else {
            entity = new UserProfileSetting();
            entity.setUserId(dto.getUserId());
            entity.setSettingKey(dto.getSettingKey());
            entity.setSettingValue(dto.getSettingValue());
        }

        UserProfileSetting saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteSetting(Integer profileSettingId) {
        repository.deleteById(profileSettingId);
    }

    @Override
    @Transactional
    public void deleteSettingByUserIdAndKey(Integer userId, String settingKey) {
        repository.deleteByUserIdAndSettingKey(userId, settingKey);
    }

    private UserProfileSettingDTO toDTO(UserProfileSetting entity) {
        UserProfileSettingDTO dto = new UserProfileSettingDTO();
        dto.setProfileSettingId(entity.getProfileSettingId());
        dto.setUserId(entity.getUserId());
        dto.setSettingKey(entity.getSettingKey());
        dto.setSettingValue(entity.getSettingValue());
        return dto;
    }

    @Override
    @Transactional
    public void deleteSettingsByUserId(Integer id) {
        repository.deleteByUserId(id);
    }
}

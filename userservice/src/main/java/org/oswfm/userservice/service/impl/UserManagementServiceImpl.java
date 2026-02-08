package org.oswfm.userservice.service.impl;

import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.mapper.UserEntityToUserMapper;
import org.oswfm.userservice.exception.UserNotFoundException;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.UserManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userEntityRepository.findAll().stream()
                .map(userEntityToUserMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer userId) {
        UserEntity entity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return userEntityToUserMapper.map(entity);
    }

    @Override
    @Transactional
    public User updateUser(Integer userId, User user) {
        UserEntity entity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        entity.setFirstName(user.getFirstName());
        entity.setMiddleName(user.getMiddleName());
        entity.setLastName(user.getLastName());
        entity.setUserName(user.getUserName());
        entity.setUserStatus(user.getUserStatus());

        UserEntity saved = userEntityRepository.save(entity);
        return userEntityToUserMapper.map(saved);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        if (!userEntityRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userEntityRepository.deleteById(userId);
    }
}

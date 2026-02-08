package org.oswfm.userservice.service;

import org.oswfm.commons.model.user.User;

import java.util.List;

public interface UserManagementService {

    List<User> getAllUsers();

    User getUserById(Integer userId);

    User updateUser(Integer userId, User user);

    void deleteUser(Integer userId);
}

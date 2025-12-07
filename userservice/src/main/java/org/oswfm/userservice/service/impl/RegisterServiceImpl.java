package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.exception.UserAlreadyExistException;
import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.mapper.RegisterRequestToUserEntityMapper;
import org.oswfm.commons.model.user.mapper.UserEntityToUserMapper;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.PasswordService;
import org.oswfm.userservice.service.RegisterService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link RegisterService} for handling user registration.
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserEntityRepository userRepository;

    private final PasswordService passwordService;

    private final RegisterRequestToUserEntityMapper registerRequestToUserEntityMapper = RegisterRequestToUserEntityMapper.initialize();

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    /**
     * Registers a new user based on the provided {@link RegisterRequest}.
     *
     * <p>This method checks if the email already exists in the database, maps the registration request to a user entity,
     * encodes the user's password, saves the user entity to the database, and returns the registered user.</p>
     *
     * @param registerRequest the request containing user registration details.
     * @return the registered {@link User}.
     * @throws UserAlreadyExistException if the email is already used for another user.
     */
    @Override
    public User registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsUserEntityByUserName(registerRequest.getUserName())) {
            throw new UserAlreadyExistException("The User Name is already used for another user : " + registerRequest.getUserName());
        }

        final UserEntity userEntityToBeSave = registerRequestToUserEntityMapper.mapForSaving(registerRequest);

        UserEntity savedUserEntity = userRepository.save(userEntityToBeSave);

        passwordService.createPassword(savedUserEntity.getUserId(), registerRequest.getPassword(), false);

        return userEntityToUserMapper.map(savedUserEntity);

    }

}

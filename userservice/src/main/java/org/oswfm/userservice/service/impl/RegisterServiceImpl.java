package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.exception.UserAlreadyExistException;
import org.oswfm.userservice.model.user.User;
import org.oswfm.userservice.model.user.dto.request.RegisterRequest;
import org.oswfm.userservice.model.user.entity.UserEntity;
import org.oswfm.userservice.model.user.mapper.RegisterRequestToUserEntityMapper;
import org.oswfm.userservice.model.user.mapper.UserEntityToUserMapper;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RegisterService} for handling user registration.
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserEntityRepository userRepository;

    private final RegisterRequestToUserEntityMapper registerRequestToUserEntityMapper = RegisterRequestToUserEntityMapper.initialize();

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    private final PasswordEncoder passwordEncoder;

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

        if (userRepository.existsUserEntityByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistException("The email is already used for another admin : " + registerRequest.getEmail());
        }

        final UserEntity userEntityToBeSave = registerRequestToUserEntityMapper.mapForSaving(registerRequest);

        userEntityToBeSave.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserEntity savedUserEntity = userRepository.save(userEntityToBeSave);

        return userEntityToUserMapper.map(savedUserEntity);

    }

}

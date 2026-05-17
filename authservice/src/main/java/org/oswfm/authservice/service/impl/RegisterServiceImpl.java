package org.oswfm.authservice.service.impl;

import org.oswfm.authservice.client.UserServiceClient;
import org.oswfm.authservice.service.RegisterService;
import org.oswfm.commons.model.common.dto.response.CustomResponse;
import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link RegisterService} interface.
 * Handles the logic for user registration by forwarding the request to the {@link UserServiceClient}.
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserServiceClient userServiceClient;

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest the registration request containing user details (email, password, etc.)
     * @return the registered {@link User} object
     */
    @Override
    public CustomResponse<User> registerUser(RegisterRequest registerRequest) {
        return userServiceClient.register(registerRequest);
    }

}

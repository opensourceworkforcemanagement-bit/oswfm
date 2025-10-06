package org.oswfm.authservice.service.impl;

import org.oswfm.authservice.client.UserServiceClient;
import org.oswfm.authservice.model.auth.User;
import org.oswfm.authservice.model.auth.dto.request.RegisterRequest;
import org.oswfm.authservice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public User registerUser(RegisterRequest registerRequest) {
        return userServiceClient.register(registerRequest).getBody();
    }

}

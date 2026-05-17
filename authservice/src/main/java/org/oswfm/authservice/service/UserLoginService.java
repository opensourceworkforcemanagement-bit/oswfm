package org.oswfm.authservice.service;

import org.oswfm.commons.model.common.dto.response.CustomResponse;
import org.oswfm.commons.model.user.dto.request.LoginRequest;
import org.oswfm.commons.model.user.dto.response.TokenResponse;

/**
 * Service interface named {@link UserLoginService} for user login operations.
 * Provides methods for handling user login and generating authentication tokens.
 */
public interface UserLoginService {

    /**
     * Logs in a user by processing the provided login request and generating authentication tokens.
     *
     * @param loginRequest the login request containing user credentials (email and password)
     * @return a {@link CustomResponse} containing the {@link TokenResponse} with authentication tokens
     */
    CustomResponse<TokenResponse> login(final LoginRequest loginRequest);

    /**
     * Validates the provided authentication token by delegating the validation to the {@link UserServiceClient}.
     *
     * @param token the authentication token to be validated
     */
    CustomResponse<Void> validateToken(String token);

}

package org.oswfm.authservice.service;

import org.oswfm.commons.model.common.dto.response.CustomResponse;
import org.oswfm.commons.model.user.dto.request.TokenRefreshRequest;
import org.oswfm.commons.model.user.dto.response.TokenResponse;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * Service interface named {@link RefreshTokenService} for handling token refresh operations.
 * Provides methods for refreshing access tokens using a refresh token.
 */
public interface RefreshTokenService {

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param tokenRefreshRequest the request containing the refresh token
     * @return a {@link CustomResponse} containing the {@link TokenResponse} with the new access and refresh tokens
     */
    CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest);

}

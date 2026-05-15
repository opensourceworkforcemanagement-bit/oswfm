package org.oswfm.userservice.service.impl;

import io.jsonwebtoken.Claims;
import org.oswfm.commons.model.user.dto.request.TokenInvalidateRequest;
import org.oswfm.userservice.service.InvalidTokenService;
import org.oswfm.userservice.service.LogoutService;
import org.oswfm.userservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link LogoutService} for handling user logout operations.
 */
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;

    /**
     * Logs out a user by invalidating their access and refresh tokens.
     * The natural JWT expiry of each token is stored alongside its ID so the
     * cleanup scheduler can prune blacklist rows once they can no longer be
     * presented as valid tokens.
     *
     * @param tokenInvalidateRequest the request containing the tokens to be invalidated.
     */
    @Override
    public void logout(TokenInvalidateRequest tokenInvalidateRequest) {

        tokenService.verifyAndValidate(
                Set.of(
                        tokenInvalidateRequest.getAccessToken(),
                        tokenInvalidateRequest.getRefreshToken()
                )
        );

        final Claims accessTokenPayload = tokenService.getPayload(tokenInvalidateRequest.getAccessToken());
        final String accessTokenId = accessTokenPayload.getId();
        final LocalDateTime accessTokenExpiry = accessTokenPayload.getExpiration()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        invalidTokenService.checkForInvalidityOfToken(accessTokenId);

        final Claims refreshTokenPayload = tokenService.getPayload(tokenInvalidateRequest.getRefreshToken());
        final String refreshTokenId = refreshTokenPayload.getId();
        final LocalDateTime refreshTokenExpiry = refreshTokenPayload.getExpiration()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        invalidTokenService.invalidateTokens(Map.of(
                accessTokenId, accessTokenExpiry,
                refreshTokenId, refreshTokenExpiry
        ));

    }

}

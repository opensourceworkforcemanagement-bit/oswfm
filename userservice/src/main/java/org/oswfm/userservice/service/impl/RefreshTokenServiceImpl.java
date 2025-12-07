package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.exception.UserNotFoundException;
import org.oswfm.userservice.exception.UserStatusNotValidException;
import org.oswfm.commons.model.user.Token;
import org.oswfm.commons.model.user.dto.request.TokenRefreshRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.enums.TokenClaims;
import org.oswfm.commons.model.user.enums.UserStatus;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.RefreshTokenService;
import org.oswfm.userservice.service.TokenService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link RefreshTokenService} for handling token refresh operations.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserEntityRepository userRepository;

    private final TokenService tokenService;

    /**
     * Refreshes the token based on the provided {@link TokenRefreshRequest}.
     *
     * <p>This method verifies and validates the provided refresh token, retrieves the associated user from the database,
     * and generates a new token for the user.</p>
     *
     * @param tokenRefreshRequest the request containing the refresh token.
     * @return a new {@link Token} generated for the user.
     * @throws UserNotFoundException if the user associated with the refresh token is not found.
     * @throws UserStatusNotValidException if the user's status is not active.
     */
    @Override
    public Token refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        tokenService.verifyAndValidate(tokenRefreshRequest.getRefreshToken());

        final Integer  userId = (Integer) tokenService
                .getPayload(tokenRefreshRequest.getRefreshToken())
                .get(TokenClaims.USER_ID.getValue());

        final UserEntity userEntityFromDB = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        this.validateUserStatus(userEntityFromDB);

        return tokenService.generateToken(
                userEntityFromDB.getClaims(),
                tokenRefreshRequest.getRefreshToken()
        );

    }

    /**
     * Validates the user status to ensure the user is active.
     *
     * @param userEntity the user entity to check.
     * @throws UserStatusNotValidException if the user's status is not active.
     */
    private void validateUserStatus(final UserEntity userEntity) {
        if (!(UserStatus.ACTIVE.equals(userEntity.getUserStatus()))) {
            throw new UserStatusNotValidException("UserStatus = " + userEntity.getUserStatus());
        }
    }

}

package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.base.AbstractBaseServiceTest;
import org.oswfm.userservice.builder.TokenBuilder;
import org.oswfm.userservice.builder.UserEntityBuilder;
import org.oswfm.userservice.exception.UserNotFoundException;
import org.oswfm.userservice.exception.UserStatusNotValidException;
import org.oswfm.commons.model.user.Token;
import org.oswfm.commons.model.user.dto.request.TokenRefreshRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.enums.UserStatus;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.TokenService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private RefreshTokenServiceImpl userRefreshTokenService;

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Test
    void refreshToken_ValidRefreshToken_ReturnsToken() {

        // Given
        String refreshTokenString = "mockRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        UserEntity mockUserEntity = new UserEntityBuilder().withValidUserFields().build();

        Claims mockClaims = TokenBuilder.getValidClaims(
                String.valueOf(mockUserEntity.getUserId()),
                mockUserEntity.getFirstName()
        );

        Token expectedToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(123456789L)
                .refreshToken("newMockRefreshToken")
                .build();

        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mockUserEntity));
        when(tokenService.generateToken(mockUserEntity.getClaims(), refreshTokenString)).thenReturn(expectedToken);

        // When
        Token actualToken = userRefreshTokenService.refreshToken(tokenRefreshRequest);

        // Then
        assertNotNull(actualToken);
        assertEquals(expectedToken.getAccessToken(), actualToken.getAccessToken());
        assertEquals(expectedToken.getAccessTokenExpiresAt(), actualToken.getAccessTokenExpiresAt());
        assertEquals(expectedToken.getRefreshToken(), actualToken.getRefreshToken());

        // Verify
        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(userRepository).findById(anyInt());
        verify(tokenService).generateToken(mockUserEntity.getClaims(), refreshTokenString);

    }

    @Test
    void refreshToken_InvalidRefreshToken_ThrowsException() {

        // Given
        String refreshTokenString = "invalidRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        doThrow(RuntimeException.class).when(tokenService).verifyAndValidate(refreshTokenString);

        // When, Then & Verify
        assertThrows(RuntimeException.class,
                () -> userRefreshTokenService.refreshToken(tokenRefreshRequest));

        verify(tokenService).verifyAndValidate(refreshTokenString);
        verifyNoInteractions(userRepository);

    }

    @Test
    void refreshToken_UserNotFound_ThrowsException() {

        // Given
        String refreshTokenString = "validRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        Claims mockClaims = TokenBuilder.getValidClaims("99", "John");

        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When, Then & Verify
        assertThrows(UserNotFoundException.class,
                () -> userRefreshTokenService.refreshToken(tokenRefreshRequest));

        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(userRepository).findById(anyInt());

    }

    @Test
    void refreshToken_InactiveUser_ThrowsException() {

        // Given
        String refreshTokenString = "validRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        UserEntity inactiveUser = new UserEntityBuilder().withValidUserFields()
                .withUserStatus(UserStatus.INACTIVE).build();

        Claims mockClaims = TokenBuilder.getValidClaims(
                String.valueOf(inactiveUser.getUserId()),
                inactiveUser.getFirstName()
        );

        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(inactiveUser));

        // When, Then & Verify
        assertThrows(UserStatusNotValidException.class,
                () -> userRefreshTokenService.refreshToken(tokenRefreshRequest));

        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(userRepository).findById(anyInt());

    }

}

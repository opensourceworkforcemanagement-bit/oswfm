package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.base.AbstractBaseServiceTest;
import org.oswfm.userservice.builder.UserEntityBuilder;
import org.oswfm.userservice.exception.PasswordNotValidException;
import org.oswfm.userservice.exception.UserNotFoundException;
import org.oswfm.commons.model.user.Token;
import org.oswfm.commons.model.user.dto.request.LoginRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.PasswordService;
import org.oswfm.userservice.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserLoginServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @Test
    void login_ValidCredentials_ReturnsToken() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .userName("johndoe1")
                .password("password123")
                .build();

        UserEntity userEntity = new UserEntityBuilder().withValidUserFields().build();

        Token expectedToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(123456789L)
                .refreshToken("mockRefreshToken")
                .build();

        PasswordService.PasswordVerificationResult validResult =
                mock(PasswordService.PasswordVerificationResult.class);
        when(validResult.isValid()).thenReturn(true);

        when(userRepository.findUserEntityByUserName(loginRequest.getUserName()))
                .thenReturn(Optional.of(userEntity));
        when(passwordService.verifyPasswordCredential(loginRequest.getUserName(), loginRequest.getPassword()))
                .thenReturn(validResult);
        when(tokenService.generateToken(userEntity.getClaims())).thenReturn(expectedToken);

        // When
        Token actualToken = userLoginService.login(loginRequest);

        // Then
        assertEquals(expectedToken.getAccessToken(), actualToken.getAccessToken());
        assertEquals(expectedToken.getRefreshToken(), actualToken.getRefreshToken());
        assertEquals(expectedToken.getAccessTokenExpiresAt(), actualToken.getAccessTokenExpiresAt());

        // Verify
        verify(userRepository).findUserEntityByUserName(loginRequest.getUserName());
        verify(passwordService).verifyPasswordCredential(loginRequest.getUserName(), loginRequest.getPassword());
        verify(tokenService).generateToken(userEntity.getClaims());

    }

    @Test
    void login_InvalidUserName_ThrowsUserNotFoundException() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .userName("nonexistent")
                .password("password123")
                .build();

        when(userRepository.findUserEntityByUserName(loginRequest.getUserName()))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(UserNotFoundException.class, () -> userLoginService.login(loginRequest));

        // Verify
        verify(userRepository).findUserEntityByUserName(loginRequest.getUserName());
        verifyNoInteractions(passwordService, tokenService);

    }

    @Test
    void login_InvalidPassword_ThrowsPasswordNotValidException() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .userName("johndoe1")
                .password("wrongPassword")
                .build();

        UserEntity userEntity = new UserEntityBuilder().withValidUserFields().build();

        PasswordService.PasswordVerificationResult invalidResult =
                mock(PasswordService.PasswordVerificationResult.class);
        when(invalidResult.isValid()).thenReturn(false);

        when(userRepository.findUserEntityByUserName(loginRequest.getUserName()))
                .thenReturn(Optional.of(userEntity));
        when(passwordService.verifyPasswordCredential(loginRequest.getUserName(), loginRequest.getPassword()))
                .thenReturn(invalidResult);

        // Then
        assertThrows(PasswordNotValidException.class, () -> userLoginService.login(loginRequest));

        // Verify
        verify(userRepository).findUserEntityByUserName(loginRequest.getUserName());
        verify(passwordService).verifyPasswordCredential(loginRequest.getUserName(), loginRequest.getPassword());
        verifyNoInteractions(tokenService);

    }

}

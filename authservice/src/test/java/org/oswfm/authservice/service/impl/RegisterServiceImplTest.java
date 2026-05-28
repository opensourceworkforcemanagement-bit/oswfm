package org.oswfm.authservice.service.impl;

import org.oswfm.authservice.base.AbstractBaseServiceTest;
import org.oswfm.authservice.client.UserServiceClient;
import org.oswfm.commons.model.common.dto.response.CustomResponse;
import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RegisterServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private RegisterServiceImpl registerService;

    @Mock
    private UserServiceClient userServiceClient;

    @Test
    void registerUser_ValidRegisterRequest_ReturnsUser() {

        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("valid.email@example.com")
                .userName("johndoe1")
                .password("validPassword123")
                .firstName("John")
                .lastName("Doe")
                .build();

        User expectedUser = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe1")
                .build();

        CustomResponse<User> expectedResponse = CustomResponse.successOf(expectedUser);

        // When
        when(userServiceClient.register(any(RegisterRequest.class)))
                .thenReturn(expectedResponse);

        // Then
        CustomResponse<User> result = registerService.registerUser(registerRequest);

        assertNotNull(result);
        assertTrue(result.getIsSuccess());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(expectedUser, result.getResponse());

        // Verify
        verify(userServiceClient, times(1)).register(any(RegisterRequest.class));

    }

    @Test
    void registerUser_InvalidRegisterRequest_ReturnsErrorResponse() {

        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("invalid.email")
                .userName("short")
                .password("short")
                .firstName("")
                .lastName("")
                .build();

        CustomResponse<User> errorResponse = CustomResponse.<User>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .isSuccess(false)
                .build();

        // When
        when(userServiceClient.register(any(RegisterRequest.class)))
                .thenReturn(errorResponse);

        // Then
        CustomResponse<User> result = registerService.registerUser(registerRequest);

        assertNotNull(result);
        assertFalse(result.getIsSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());

        // Verify
        verify(userServiceClient, times(1)).register(any(RegisterRequest.class));

    }

}

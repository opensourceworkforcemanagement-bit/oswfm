package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.base.AbstractBaseServiceTest;
import org.oswfm.userservice.exception.UserAlreadyExistException;
import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.mapper.RegisterRequestToUserEntityMapper;
import org.oswfm.commons.model.user.mapper.UserEntityToUserMapper;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private RegisterServiceImpl userRegisterService;

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private PasswordService passwordService;

    private final RegisterRequestToUserEntityMapper registerRequestToUserEntityMapper =
            RegisterRequestToUserEntityMapper.initialize();

    private final UserEntityToUserMapper userEntityToUserMapper =
            UserEntityToUserMapper.initialize();

    @Test
    void givenUserRegisterRequest_whenRegisterUser_thenReturnUser() {

        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .userName("johndoe1")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        UserEntity userEntity = registerRequestToUserEntityMapper.mapForSaving(request);
        User expected = userEntityToUserMapper.map(userEntity);

        // When
        when(userRepository.existsUserEntityByUserName(request.getUserName())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Then
        User result = userRegisterService.registerUser(request);

        assertEquals(expected.getUserName(), result.getUserName());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());

        // Verify
        verify(userRepository).save(any(UserEntity.class));

    }

    @Test
    void givenUserRegisterRequest_whenUserNameAlreadyExists_thenThrowUserAlreadyExistException() {

        // Given
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .userName("johndoe1")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .build();

        // When
        when(userRepository.existsUserEntityByUserName(request.getUserName())).thenReturn(true);

        // Then
        assertThrows(UserAlreadyExistException.class, () -> userRegisterService.registerUser(request));

        // Verify
        verify(userRepository, never()).save(any(UserEntity.class));

    }

}

package org.oswfm.userservice.controller;

import java.util.List;

import org.oswfm.commons.model.common.dto.response.CustomResponse;
import org.oswfm.commons.model.user.Token;
import org.oswfm.commons.model.user.User;
import org.oswfm.commons.model.user.dto.request.LoginRequest;
import org.oswfm.commons.model.user.dto.request.RegisterRequest;
import org.oswfm.commons.model.user.dto.request.TokenInvalidateRequest;
import org.oswfm.commons.model.user.dto.request.TokenRefreshRequest;
import org.oswfm.commons.model.user.dto.response.TokenResponse;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.mapper.TokenToTokenResponseMapper;
import org.oswfm.commons.model.user.mapper.UserEntityToUserMapper;
import org.oswfm.userservice.model.dto.UserProfileSettingDTO;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.LogoutService;
import org.oswfm.userservice.service.RefreshTokenService;
import org.oswfm.userservice.service.RegisterService;
import org.oswfm.userservice.service.TokenService;
import org.oswfm.userservice.service.UserLoginService;
import org.oswfm.userservice.service.UserManagementService;
import org.oswfm.userservice.service.UserProfileSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller named {@link UserController} for managing user-related operations.
 * Provides endpoints for user registration, token validation, login, token refresh, logout, and authentication retrieval.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final RegisterService registerService;

    private final TokenService tokenService;

    private final UserLoginService userLoginService;

    private final RefreshTokenService refreshTokenService;

    private final LogoutService logoutService;

    private final UserEntityRepository userEntityRepository;

    private final UserManagementService userManagementService;

    private final UserProfileSettingService userProfileSettingService;

    private final TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    /**
     * Registers a new user.
     *
     * @param registerRequest the user registration request containing user details
     * @return a {@link CustomResponse} indicating the success of the registration
     */
    @PostMapping("/register")
    public CustomResponse<User> registerUser(@RequestBody @Validated final RegisterRequest registerRequest) {
        log.info("UserController | registerUser");
        final User registeredUser = registerService.registerUser(registerRequest);
        return CustomResponse.successOf(registeredUser);
    }

    /**
     * Validates the provided token.
     *
     * @param token the token to be validated
     * @return a {@link ResponseEntity} with an HTTP status code indicating the validation result
     */
    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        log.info("UserController | validateToken");
        tokenService.verifyAndValidate(token);
        return ResponseEntity.ok().build();
    }

    /**
     * Logs in a user and generates a new token.
     *
     * @param loginRequest the login request containing user credentials
     * @return a {@link CustomResponse} containing the generated {@link TokenResponse}
     */
    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
        log.info("UserController | login");
        final Token token = userLoginService.login(loginRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    /**
     * Refreshes the token based on the provided refresh request.
     *
     * @param tokenRefreshRequest the token refresh request containing the refresh token
     * @return a {@link CustomResponse} containing the new {@link TokenResponse}
     */
    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        log.info("UserController | refreshToken");
        final Token token = refreshTokenService.refreshToken(tokenRefreshRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    /**
     * Logs out a user by invalidating the provided token.
     *
     * @param tokenInvalidateRequest the request containing the token to be invalidated
     * @return a {@link CustomResponse} indicating the success of the logout operation
     */
    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        log.info("UserController | logout");
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

    /**
     * Retrieves all active users (userStatus = 1).
     *
     * @return a {@link CustomResponse} containing a list of active {@link User} objects
     */
    @GetMapping("/active")
    public CustomResponse<List<User>> getActiveUsers() {
        log.info("UserController | getActiveUsers");
        final List<UserEntity> activeEntities = userEntityRepository.findByUserStatus(1);
        final List<User> activeUsers = activeEntities.stream()
                .map(userEntityToUserMapper::map)
                .toList();
        return CustomResponse.successOf(activeUsers);
    }

    /**
     * Retrieves the authentication details for the provided token.
     *
     * @param token the token for which to retrieve authentication details
     * @return a {@link ResponseEntity} containing the {@link UsernamePasswordAuthenticationToken} for the token
     */
    @GetMapping("/authenticate")
    public ResponseEntity<UsernamePasswordAuthenticationToken> getAuthentication(@RequestParam String token) {
        UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(token);
        return ResponseEntity.ok(authentication);
    }

    // ========== User CRUD Endpoints ==========

    /**
     * Retrieves all users.
     *
     * @return a {@link CustomResponse} containing a list of all {@link User} objects
     */
    @GetMapping
    public CustomResponse<List<User>> getAllUsers() {
        log.info("UserController | getAllUsers");
        return CustomResponse.successOf(userManagementService.getAllUsers());
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the user ID
     * @return a {@link CustomResponse} containing the {@link User}
     */
    @GetMapping("/{id}")
    public CustomResponse<User> getUserById(@PathVariable Integer id) {
        log.info("UserController | getUserById | id: {}", id);
        return CustomResponse.successOf(userManagementService.getUserById(id));
    }

    /**
     * Updates an existing user.
     *
     * @param id the user ID
     * @param user the updated user data
     * @return a {@link CustomResponse} containing the updated {@link User}
     */
    @PutMapping("/{id}")
    public CustomResponse<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("UserController | updateUser | id: {}", id);
        return CustomResponse.successOf(userManagementService.updateUser(id, user));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the user ID
     * @return a {@link CustomResponse} indicating success
     */
    @DeleteMapping("/{id}")
    public CustomResponse<Void> deleteUser(@PathVariable Integer id) {
        log.info("UserController | deleteUser | id: {}", id);
        userProfileSettingService.deleteSettingsByUserId(id); // Clean up profile settings before deleting user
        userManagementService.deleteUser(id);
        return CustomResponse.SUCCESS;
    }

    // ========== Profile Settings Endpoints ==========

    /**
     * Retrieves all profile settings for a user.
     *
     * @param userId the user ID
     * @return a {@link CustomResponse} containing a list of {@link UserProfileSettingDTO}
     */
    @GetMapping("/{userId}/profile-settings")
    public CustomResponse<List<UserProfileSettingDTO>> getProfileSettings(@PathVariable Integer userId) {
        log.info("UserController | getProfileSettings | userId: {}", userId);
        return CustomResponse.successOf(userProfileSettingService.getSettingsByUserId(userId));
    }

    /**
     * Saves (creates or updates) a profile setting for a user.
     *
     * @param userId the user ID
     * @param dto the profile setting data
     * @return a {@link CustomResponse} containing the saved {@link UserProfileSettingDTO}
     */
    @PostMapping("/{userId}/profile-settings")
    public CustomResponse<UserProfileSettingDTO> saveProfileSetting(
            @PathVariable Integer userId,
            @RequestBody UserProfileSettingDTO dto) {
        log.info("UserController | saveProfileSetting | userId: {}", userId);
        dto.setUserId(userId);
        return CustomResponse.successOf(userProfileSettingService.saveSetting(dto));
    }

    /**
     * Deletes a profile setting.
     *
     * @param userId the user ID
     * @param settingId the profile setting ID
     * @return a {@link CustomResponse} indicating success
     */
    @DeleteMapping("/{userId}/profile-settings/{settingId}")
    public CustomResponse<Void> deleteProfileSetting(
            @PathVariable Integer userId,
            @PathVariable Integer settingId) {
        log.info("UserController | deleteProfileSetting | userId: {} | settingId: {}", userId, settingId);
        userProfileSettingService.deleteSetting(settingId);
        return CustomResponse.SUCCESS;
    }

}

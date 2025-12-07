package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.exception.PasswordNotValidException;
import org.oswfm.userservice.exception.UserNotFoundException;
import org.oswfm.commons.model.user.Token;
import org.oswfm.commons.model.user.dto.request.LoginRequest;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.userservice.repository.UserEntityRepository;
import org.oswfm.userservice.service.PasswordService;
import org.oswfm.userservice.service.PasswordService.PasswordVerificationResult;
import org.oswfm.userservice.service.TokenService;
import org.oswfm.userservice.service.UserLoginService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link UserLoginService} for handling user login operations.
 * This service handles user authentication by validating login credentials and generating JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserEntityRepository userRepository;

    private final TokenService tokenService;

    private final PasswordService passwordService;

    /**
     * Authenticates a user based on the provided login request and generates a JWT token upon successful login.
     * This method retrieves the user entity from the database using the email provided in the login request.
     * It then validates the provided password against the stored password. If the credentials are valid,
     * it generates and returns a JWT token containing the user's claims.
     *
     * @param loginRequest the {@link LoginRequest} object containing the user's email and password.
     * @return a {@link Token} object containing the generated JWT token.
     * @throws UserNotFoundException if no user is found with the given email.
     * @throws PasswordNotValidException if the provided password does not match the stored password.
     */
    @Override
    public Token login(LoginRequest loginRequest) {

        final UserEntity userEntityFromDB = userRepository
                .findUserEntityByUserName(loginRequest.getUserName()).orElseThrow(
                        () -> new UserNotFoundException("Can't find with given user name: " + loginRequest.getUserName())                    
                );
                
        PasswordVerificationResult passwordVerificationResult  = passwordService.verifyPasswordCredential(loginRequest.getUserName(), loginRequest.getPassword());

        if (!passwordVerificationResult.isValid()) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(userEntityFromDB.getClaims());

    }

}

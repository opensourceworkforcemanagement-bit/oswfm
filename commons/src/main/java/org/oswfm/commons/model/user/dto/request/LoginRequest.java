package org.oswfm.commons.model.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a login request named {@link LoginRequest} containing the user's userName and password.
 */
@Getter
@Setter
@Builder
public class LoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}

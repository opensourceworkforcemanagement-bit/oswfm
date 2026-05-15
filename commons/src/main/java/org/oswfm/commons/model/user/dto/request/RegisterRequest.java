package org.oswfm.commons.model.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request named {@link RegisterRequest} for user registration.
 * This class contains the necessary details required to register a new user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Email(message = "Please enter valid e-mail address")
    private String email;

    @NotBlank(message = "User name can't be blank.")
    @Size(min = 7, message = "Minimum user name length is 7 characters.")
    private String userName;

    @NotBlank(message = "Password can't be blank.")
    @Size(min = 8, message = "Minimum password length is 8 characters.")
    private String password;

    @NotBlank(message = "First name can't be blank.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name can't be blank.")
    private String lastName;
}

package org.oswfm.commons.model.user;

import org.oswfm.commons.model.common.BaseDomainModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user domain object named {@link User} in the system.
 * The {@code User} class is a domain model that contains user-related information such as
 * identification, contact details, status, type, and password. It extends {@link BaseDomainModel}
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDomainModel {

    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer userStatus;
    private String userName; 
}

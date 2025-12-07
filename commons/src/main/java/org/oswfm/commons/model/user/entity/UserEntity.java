package org.oswfm.commons.model.user.entity;

import java.util.HashMap;
import java.util.Map;

import org.oswfm.commons.model.user.enums.TokenClaims;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user entity named {@link UserEntity} in the system.
 * This entity stores user-related information such as email, password, and personal details.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oswfm_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Considering -- @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private Integer  userId;
    
    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    
    @Column(name = "USER_STATUS")
    @Default
    private Integer  userStatus = 1; // Active by default

    // @java.lang.SuppressWarnings(value = "all")
    // @lombok.Generated
    // protected UserEntity(final UserEntity.UserEntityBuilder<?, ?> b) {
    //     this.userStatus = UserStatus.ACTIVE;
    // }

    // @java.lang.SuppressWarnings(value = "all")
    // @lombok.Generated
    // public UserEntity() {
    //     this.userStatus = UserStatus.ACTIVE;
    // }

    // @java.lang.SuppressWarnings(value = "all")
    // @lombok.Generated
    // public UserEntity(final String id, final Integer  userId, final String username, final String email, final String password, final String firstName, final String lastName, final String phoneNumber, final UserType userType, final UserStatus userStatus) {
    //     this.userStatus = UserStatus.ACTIVE;
    // }

    /**
     * Constructs a map of claims based on the user's attributes.
     * This map is typically used to create JWT claims for the user.
     * @return a map of claims containing user attributes
     */
    public Map<String, Object> getClaims() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TokenClaims.USER_ID.getValue(), this.userId);
        claims.put(TokenClaims.USER_NAME.getValue(), this.userName);
        claims.put(TokenClaims.USER_STATUS.getValue(), this.userStatus);
        claims.put(TokenClaims.USER_FIRST_NAME.getValue(), this.firstName);
        claims.put(TokenClaims.USER_MIDDLE_NAME.getValue(), this.middleName);
        claims.put(TokenClaims.USER_LAST_NAME.getValue(), this.lastName);
        return claims;
    }

}

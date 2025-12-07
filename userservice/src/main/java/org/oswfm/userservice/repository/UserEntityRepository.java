package org.oswfm.userservice.repository;

import java.util.Optional;

import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link UserEntity} instances.
 * Provides CRUD operations and additional query methods for {@link UserEntity}.
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer > {

    /**
     * Checks if a {@link UserEntity} with the given user name exists.
     *
     * @param userName the user name to check.
     * @return {@code true} if a user with the specified user name exists, otherwise {@code false}.
     */
    boolean existsUserEntityByUserName(final String userName);


    /**
     * Finds a {@link UserEntity} by its user name.
     *
     * @param userName the user name.
     * @return {@link UserEntity} if found.
     */
     Optional<UserEntity> findUserEntityByUserName(final String userName);


    // /**
    //  * Checks if a {@link UserEntity} with the given email exists.
    //  *
    //  * @param email the email to check.
    //  * @return {@code true} if a user with the specified email exists, otherwise {@code false}.
    //  */
    // boolean existsUserEntityByEmail(final String email);

    // /**
    //  * Finds a {@link UserEntity} by its email.
    //  *
    //  * @param email the email to search for.
    //  * @return an {@link Optional} containing the {@link UserEntity} if found, or {@link Optional#empty()} if not.
    //  */
    // Optional<UserEntity> findUserEntityByEmail(final String email);

}

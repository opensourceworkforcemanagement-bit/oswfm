package org.oswfm.userservice.repository;

import org.oswfm.userservice.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} instances.
 * Provides CRUD operations and additional query methods for {@link UserEntity}.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {



}

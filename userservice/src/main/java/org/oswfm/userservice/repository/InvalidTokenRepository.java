package org.oswfm.userservice.repository;

import org.oswfm.commons.model.user.entity.InvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository interface for managing {@link InvalidTokenEntity} instances.
 * Provides CRUD operations and additional query methods for {@link InvalidTokenEntity}.
 */
public interface InvalidTokenRepository extends JpaRepository<InvalidTokenEntity, Long> {

    /**
     * Finds an {@link InvalidTokenEntity} by its token ID.
     *
     * @param tokenId the token ID to search for.
     * @return an {@link Optional} containing the {@link InvalidTokenEntity} if found, or {@link Optional#empty()} if not.
     */
    Optional<InvalidTokenEntity> findByTokenId(final String tokenId);

    /**
     * Deletes all {@link InvalidTokenEntity} rows whose natural expiry has passed.
     * Called hourly by {@code InvalidTokenCleanupScheduler}.
     *
     * @param now the cutoff timestamp; rows with expiresAt at or before this value are removed.
     */
    @Modifying
    @Query("DELETE FROM InvalidTokenEntity t WHERE t.expiresAt <= :now")
    void deleteAllExpiredBefore(LocalDateTime now);

}

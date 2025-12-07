package org.oswfm.userservice.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.oswfm.commons.model.user.entity.PasswordCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordCredentialRepository extends JpaRepository<PasswordCredential, Integer > {
    
    Optional<PasswordCredential> findByUser_UserId(Integer userId);
    
    PasswordCredential findByUser_UserName(String userName);
    
    boolean existsByUser_UserId(Integer userId);
    
    @Modifying
    @Query("UPDATE PasswordCredential pc SET pc.failedAttempts = pc.failedAttempts + 1 " +
           "WHERE pc.user.userId = :userId")
    void incrementFailedAttempts(@Param("userId") Integer  userId);
    
    @Modifying
    @Query("UPDATE PasswordCredential pc SET pc.failedAttempts = 0, pc.lockedUntil = null " +
           "WHERE pc.user.userId = :userId")
    void resetFailedAttempts(@Param("userId") Integer  userId);
    
    @Modifying
    @Query("UPDATE PasswordCredential pc SET pc.lockedUntil = :lockedUntil " +
           "WHERE pc.user.userId = :userId")
    void lockAccount(@Param("userId") Integer  userId, @Param("lockedUntil") OffsetDateTime lockedUntil);
}

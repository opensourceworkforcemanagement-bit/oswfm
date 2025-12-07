package org.oswfm.commons.model.user.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity for storing user password credentials
 * Follows OWASP password storage guidelines
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_credentials")
public class PasswordCredential {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Integer  credentialId;
    
    //@TODO: Change to UserEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;
    
    /**
     * Hashed password using bcrypt/argon2
     * Never store plaintext passwords
     */
    @Column(name = "password_hash", nullable = false, length = 512)
    private String passwordHash;
    
    /**
     * Unique salt per password (even though bcrypt includes salt)
     * Additional security layer
     */
    @Column(name = "salt", nullable = false, length = 128)
    private String salt;
    
    /**
     * Algorithm used for hashing (bcrypt, argon2, pbkdf2)
     */
    @Column(name = "algorithm", nullable = false, length = 50)
    private String algorithm = "BCRYPT";
    
    /**
     * Algorithm parameters (e.g., cost factor for bcrypt)
     */
    @Column(name = "algorithm_params", length = 255)
    private String algorithmParams;
    
    /**
     * Number of failed login attempts
     */
    @Column(name = "failed_attempts")
    private Integer  failedAttempts = 0;
    
    /**
     * Timestamp when account was locked due to failed attempts
     */
    @Column(name = "locked_until")
    private OffsetDateTime lockedUntil;
    
    /**
     * Timestamp of last password change
     */
    @Column(name = "last_changed_at")
    private OffsetDateTime lastChangedAt;
    
    /**
     * Timestamp when password expires and must be changed
     */
    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;
    
    /**
     * Whether password must be changed on next login
     */
    @Column(name = "must_change_password")
    private Boolean mustChangePassword = false;
    
    /**
     * Previous password hashes to prevent reuse
     * Stored as JSON array
     */
    @Column(name = "password_history", columnDefinition = "TEXT")
    private String passwordHistory;
    
    /**
     * Version for optimistic locking
     */
    @Version
    @Column(name = "version")
    private Long version;
    
    // @CreationTimestamp
    // @Column(name = "created_at", updatable = false)
    // private OffsetDateTime createdAt;
        
    /**
     * Check if account is currently locked
     */
    public boolean isLocked() {
        if (lockedUntil == null) {
            return false;
        }
        return OffsetDateTime.now().isBefore(lockedUntil);
    }
    
    /**
     * Check if password has expired
     */
    public boolean isPasswordExpired() {
        if (expiresAt == null) {
            return false;
        }
        return OffsetDateTime.now().isAfter(expiresAt);
    }
}

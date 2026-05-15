package org.oswfm.commons.model.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.oswfm.commons.model.common.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Represents an entity named {@link InvalidTokenEntity} for storing invalid tokens in the system.
 * This entity tracks tokens that have been invalidated to prevent their reuse.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "INVALID_TOKEN")
public class InvalidTokenEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVALID_TOKEN_SEQ")
    @SequenceGenerator(name = "INVALID_TOKEN_SEQ", sequenceName = "INVALID_TOKEN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "TOKEN_ID", nullable = false)
    private String tokenId;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    public InvalidTokenEntity(String tokenId, LocalDateTime expiresAt) {
        this.tokenId = tokenId;
        this.expiresAt = expiresAt;
    }

}

package org.oswfm.commons.model.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents an entity named {@link InvalidTokenEntity} for storing invalid tokens in the system.
 * This entity tracks tokens that have been invalidated to prevent their reuse.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVALID_TOKEN")
public class InvalidTokenEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(name = "TOKEN_ID")
    private String tokenId;

}

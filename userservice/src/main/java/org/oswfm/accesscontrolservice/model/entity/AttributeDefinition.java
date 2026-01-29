package org.oswfm.accesscontrolservice.model.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute_definitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Integer  attributeId;
    
    @Column(name = "attribute_name", unique = true, nullable = false)
    private String attributeName;
    
    @Column(name = "attribute_category", nullable = false)
    private String attributeCategory;
    
    @Column(name = "data_type", nullable = false)
    private String dataType;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "is_required")
    private Boolean isRequired = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}

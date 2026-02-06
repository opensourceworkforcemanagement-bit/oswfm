package org.oswfm.accesscontrolservice.model.entity;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_category_id", nullable = false)
    private AttributeCategory attributeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_type_id", nullable = false)
    private DataType dataType;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "is_required")
    private Boolean isRequired = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}

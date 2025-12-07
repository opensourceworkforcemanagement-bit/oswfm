package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, Integer > {
    
    Optional<AttributeDefinition> findByAttributeName(String attributeName);
    
    List<AttributeDefinition> findByAttributeCategory(String attributeCategory);
    
    List<AttributeDefinition> findByIsRequired(Boolean isRequired);
    
    boolean existsByAttributeName(String attributeName);
}

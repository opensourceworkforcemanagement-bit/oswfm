package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer > {
    
    List<Resource> findByResourceType(String resourceType);
    
    List<Resource> findByIsActive(Boolean isActive);
    
    List<Resource> findByOwner_UserId( Integer ownerId);
    
    List<Resource> findByResourceTypeAndIsActive(String resourceType, Boolean isActive);
}

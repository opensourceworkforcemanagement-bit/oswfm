package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    List<Resource> findByResourceType_ResourceTypeId(Integer resourceTypeId);

    List<Resource> findByIsActive(Boolean isActive);

    List<Resource> findByOwner_UserId(Integer ownerId);

    List<Resource> findByResourceType_ResourceTypeIdAndIsActive(Integer resourceTypeId, Boolean isActive);
}

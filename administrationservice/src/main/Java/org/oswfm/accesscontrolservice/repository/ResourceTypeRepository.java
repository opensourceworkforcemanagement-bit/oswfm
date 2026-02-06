package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer> {

    Optional<ResourceType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);
}

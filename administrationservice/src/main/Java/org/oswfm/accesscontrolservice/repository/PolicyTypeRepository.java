package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyTypeRepository extends JpaRepository<PolicyType, Integer> {

    Optional<PolicyType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);
}

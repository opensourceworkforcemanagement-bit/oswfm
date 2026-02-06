package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.ObligationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObligationTypeRepository extends JpaRepository<ObligationType, Integer> {

    Optional<ObligationType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);
}

package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer > {

    Optional<Operation> findByOperationName(String operationName);

    boolean existsByOperationName(String operationName);
}

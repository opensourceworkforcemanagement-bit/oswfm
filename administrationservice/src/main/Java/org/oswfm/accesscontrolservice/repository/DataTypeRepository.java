package org.oswfm.accesscontrolservice.repository;

import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Integer> {

    Optional<DataType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);
}

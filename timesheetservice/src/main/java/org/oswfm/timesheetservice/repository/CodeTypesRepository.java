package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.CodeTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeTypesRepository extends JpaRepository<CodeTypes, Integer> {

    Optional<CodeTypes> findByCodeTypeName(String codeTypeName);

    boolean existsByCodeTypeName(String codeTypeName);
}

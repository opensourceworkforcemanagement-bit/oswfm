package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkforceCodesRepository extends JpaRepository<WorkforceCodes, Integer> {

    // Custom query methods
    List<WorkforceCodes> findByCodeType(String codeType);

    List<WorkforceCodes> findByStatus(Integer status);

    List<WorkforceCodes> findByCodeTypeAndStatus(String codeType, Integer status);
}

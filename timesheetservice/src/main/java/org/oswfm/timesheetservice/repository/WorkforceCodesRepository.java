package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkforceCodesRepository extends JpaRepository<WorkforceCodes, Integer> {

    // Custom query methods
    List<WorkforceCodes> findByCodeTypeId(Integer codeTypeId);

    List<WorkforceCodes> findByStatus(Integer status);

    List<WorkforceCodes> findByCodeTypeIdAndStatus(Integer codeTypeId, Integer status);
}

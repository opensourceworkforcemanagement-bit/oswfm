package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetNormalized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetNormalizedRepository extends JpaRepository<TimesheetNormalized, Integer> {

    List<TimesheetNormalized> findByEmployeeId(Integer employeeId);

    List<TimesheetNormalized> findByPayPeriodId(Integer payPeriodId);

    List<TimesheetNormalized> findByStatus(Integer status);

    List<TimesheetNormalized> findByEmployeeIdAndPayPeriodId(Integer employeeId, Integer payPeriodId);

    List<TimesheetNormalized> findByEmployeeIdAndStatus(Integer employeeId, Integer status);

    Optional<TimesheetNormalized> findByEmployeeIdAndPayPeriodIdAndTimesheetTypeId(
            Integer employeeId, Integer payPeriodId, Integer timesheetTypeId);
}

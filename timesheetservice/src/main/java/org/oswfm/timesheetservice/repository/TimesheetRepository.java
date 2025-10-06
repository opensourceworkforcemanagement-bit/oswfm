package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {
    // Add custom query methods here if needed
}

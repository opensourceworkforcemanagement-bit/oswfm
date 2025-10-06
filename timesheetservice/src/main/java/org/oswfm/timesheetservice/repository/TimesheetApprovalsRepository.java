package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetApprovalsRepository extends JpaRepository<TimesheetApprovals, Integer> {
    // Add custom query methods here if needed
}

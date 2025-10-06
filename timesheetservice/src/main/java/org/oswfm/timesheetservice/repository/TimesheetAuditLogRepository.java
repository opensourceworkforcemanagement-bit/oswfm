package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetAuditLogRepository extends JpaRepository<TimesheetAuditLog, Integer> {
    // Add custom query methods here if needed
}

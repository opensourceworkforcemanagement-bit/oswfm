package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetOldAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetOldAuditLogRepository extends JpaRepository<TimesheetOldAuditLog, Integer> {
    // Add custom query methods here if needed
}

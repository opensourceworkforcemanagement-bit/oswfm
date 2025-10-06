package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLogArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetAuditLogArchiveRepository extends JpaRepository<TimesheetAuditLogArchive, Integer> {
    // Add custom query methods here if needed
}

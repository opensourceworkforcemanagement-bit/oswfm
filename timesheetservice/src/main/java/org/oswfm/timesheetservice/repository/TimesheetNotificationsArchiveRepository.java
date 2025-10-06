package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetNotificationsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetNotificationsArchiveRepository extends JpaRepository<TimesheetNotificationsArchive, Integer> {
    // Add custom query methods here if needed
}

package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetNotifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetNotificationsRepository extends JpaRepository<TimesheetNotifications, Integer> {
    // Add custom query methods here if needed
}

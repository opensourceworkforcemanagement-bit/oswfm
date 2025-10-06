package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOutOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesInOutOldRepository extends JpaRepository<TimesheetEntriesInOutOld, Integer> {
    // Add custom query methods here if needed
}

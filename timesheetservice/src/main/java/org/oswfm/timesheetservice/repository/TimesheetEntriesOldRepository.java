package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesOldRepository extends JpaRepository<TimesheetEntriesOld, Integer> {
    // Add custom query methods here if needed
}

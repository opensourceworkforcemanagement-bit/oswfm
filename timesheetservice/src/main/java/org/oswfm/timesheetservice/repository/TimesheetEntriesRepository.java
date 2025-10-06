package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesRepository extends JpaRepository<TimesheetEntries, Integer> {
    // Add custom query methods here if needed
}

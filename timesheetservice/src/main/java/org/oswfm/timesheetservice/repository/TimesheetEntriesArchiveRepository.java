package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesArchiveRepository extends JpaRepository<TimesheetEntriesArchive, Integer> {
    // Add custom query methods here if needed
}

package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOutArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesInOutArchiveRepository extends JpaRepository<TimesheetEntriesInOutArchive, Integer> {
    // Add custom query methods here if needed
}

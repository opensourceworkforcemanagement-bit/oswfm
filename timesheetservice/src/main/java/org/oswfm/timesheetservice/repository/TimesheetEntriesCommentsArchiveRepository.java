package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesCommentsArchiveRepository extends JpaRepository<TimesheetEntriesCommentsArchive, Integer> {
    // Add custom query methods here if needed
}

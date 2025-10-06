package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntriesCommentsRepository extends JpaRepository<TimesheetEntriesComments, Integer> {
    // Add custom query methods here if needed
}

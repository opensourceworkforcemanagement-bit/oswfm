package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRemarksArchiveRepository extends JpaRepository<TimesheetRemarksArchive, Integer> {
    // Add custom query methods here if needed
}

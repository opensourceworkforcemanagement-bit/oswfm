package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRemarksRepository extends JpaRepository<TimesheetRemarks, Integer> {
    // Add custom query methods here if needed
}

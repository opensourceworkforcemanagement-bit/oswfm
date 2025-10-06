package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRemarksOldRepository extends JpaRepository<TimesheetRemarksOld, Integer> {
    // Add custom query methods here if needed
}

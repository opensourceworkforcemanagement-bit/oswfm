package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetSummaryRepository extends JpaRepository<TimesheetSummary, Integer> {
    // Add custom query methods here if needed
}

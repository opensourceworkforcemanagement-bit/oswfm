package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetOperationsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetOperationsTypesRepository extends JpaRepository<TimesheetOperationsTypes, Integer> {
    // Add custom query methods here if needed
}

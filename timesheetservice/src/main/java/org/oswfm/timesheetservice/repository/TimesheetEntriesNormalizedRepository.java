package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetEntriesNormalizedRepository extends JpaRepository<TimesheetEntriesNormalized, Integer> {

    List<TimesheetEntriesNormalized> findByTimesheetNormalizedId(Integer timesheetNormalizedId);

    void deleteByTimesheetNormalizedId(Integer timesheetNormalizedId);
}

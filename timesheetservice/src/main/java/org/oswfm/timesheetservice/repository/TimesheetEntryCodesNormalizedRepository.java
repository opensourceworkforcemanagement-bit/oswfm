package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodes;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetEntryCodesNormalizedRepository extends JpaRepository<TimesheetEntryCodes, TimesheetEntryCodesId> {

    List<TimesheetEntryCodes> findByIdTimesheetEntriesId(Integer timesheetEntriesId);

    List<TimesheetEntryCodes> findByIdWorkforceCodesId(Integer workforceCodesId);

    void deleteByIdTimesheetEntriesId(Integer timesheetEntriesId);

    void deleteByIdWorkforceCodesId(Integer workforceCodesId);

    boolean existsByIdTimesheetEntriesIdAndIdWorkforceCodesId(Integer timesheetEntriesId, Integer workforceCodesId);
}

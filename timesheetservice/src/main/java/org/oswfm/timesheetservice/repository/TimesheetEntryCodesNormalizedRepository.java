package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodes;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetEntryCodesNormalizedRepository extends JpaRepository<TimesheetEntryCodes, TimesheetEntryCodesId> {

    List<TimesheetEntryCodes> findByIdTimesheetEntriesId(Long timesheetEntriesId);

    List<TimesheetEntryCodes> findByIdWorkforceCodesId(Long workforceCodesId);

    void deleteByIdTimesheetEntriesId(Long timesheetEntriesId);

    void deleteByIdWorkforceCodesId(Long workforceCodesId);

    boolean existsByIdTimesheetEntriesIdAndIdWorkforceCodesId(Long timesheetEntriesId, Long workforceCodesId);
}

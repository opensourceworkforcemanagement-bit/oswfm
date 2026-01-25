package org.oswfm.timesheetservice.repository;

import java.util.List;

import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntryCode;
import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntryCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetEntryCodeRepository extends JpaRepository<TimesheetEntryCode, TimesheetEntryCodeId> {

    List<TimesheetEntryCode> findByTimesheetEntry(Long timesheetEntryId);

    List<TimesheetEntryCode> findByWorkforceCode(Long workforceCodeId);
}

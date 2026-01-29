package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimesheetEntryMinutesNormalizedRepository extends JpaRepository<TimesheetEntryMinutes, Long> {

    List<TimesheetEntryMinutes> findByTimesheetEntriesId(Long timesheetEntriesId);

    List<TimesheetEntryMinutes> findByTimesheetEntriesIdAndDayOfWeek(Long timesheetEntriesId, String dayOfWeek);

    List<TimesheetEntryMinutes> findByTimesheetEntriesIdAndDate(Long timesheetEntriesId, Date date);

    void deleteByTimesheetEntriesId(Long timesheetEntriesId);
}

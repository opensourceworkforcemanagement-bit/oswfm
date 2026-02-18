package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimesheetEntryMinutesNormalizedRepository extends JpaRepository<TimesheetEntryMinutes, Integer> {

    List<TimesheetEntryMinutes> findByTimesheetEntriesId(Integer timesheetEntriesId);

    List<TimesheetEntryMinutes> findByTimesheetEntriesIdAndDayOfWeek(Integer timesheetEntriesId, String dayOfWeek);

    List<TimesheetEntryMinutes> findByTimesheetEntriesIdAndDate(Integer timesheetEntriesId, Date date);

    void deleteByTimesheetEntriesId(Integer timesheetEntriesId);
}

package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntryMinutes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetEntryMinutesRepository extends JpaRepository<TimesheetEntryMinutes, Integer> {

    List<TimesheetEntryMinutes> findByTimesheetEntryId(Long timesheetEntryId);

    Optional<TimesheetEntryMinutes> findByTimesheetEntryIdAndDate(
        Long timesheetEntryId,
        LocalDate date
    );

    List<TimesheetEntryMinutes> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(m.minutes) FROM TimesheetEntryMinutes m " +
           "WHERE m.timesheetEntryId = :entryId")
    BigDecimal sumMinutesByEntry(@Param("entryId") Long entryId);

    @Query("SELECT SUM(m.minutes) FROM TimesheetEntryMinutes m " +
           "WHERE m.timesheetEntryId IN (SELECT te.timesheetEntryId FROM TimesheetEntry te WHERE te.timesheetId = :timesheetId)")
    BigDecimal sumMinutesByTimesheet(@Param("timesheetId") Integer timesheetId);
}

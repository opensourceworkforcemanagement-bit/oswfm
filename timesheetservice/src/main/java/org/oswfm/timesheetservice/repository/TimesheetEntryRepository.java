package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized.TimesheetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry, Integer> {

    List<TimesheetEntry> findByTimesheetId(Integer timesheetId);

    @Query("SELECT DISTINCT te FROM TimesheetEntry te " +
           "LEFT JOIN FETCH te.codes " +
           "LEFT JOIN FETCH te.minutes " +
           "WHERE te.timesheetEntryId = :id")
    Optional<TimesheetEntry> findByIdWithCodesAndHours(@Param("id") Integer id);

    @Query("SELECT DISTINCT te FROM TimesheetEntry te " +
           "LEFT JOIN FETCH te.codes " +
           "LEFT JOIN FETCH te.minutes " +
           "WHERE te.timesheetId = :timesheetId")
    List<TimesheetEntry> findByTimesheetIdWithCodesAndHours(@Param("timesheetId") Integer timesheetId);

    @Query("SELECT te FROM TimesheetEntry te " +
           "JOIN te.codes c " +
           "WHERE c.workforceCode = :workforceCodeId")
    List<TimesheetEntry> findByWorkforceCodeId(@Param("workforceCodeId") Long workforceCodeId);

    @Query("SELECT te FROM TimesheetEntry te " +
           "JOIN te.minutes m " +
           "WHERE m.date BETWEEN :startDate AND :endDate")
    List<TimesheetEntry> findByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}

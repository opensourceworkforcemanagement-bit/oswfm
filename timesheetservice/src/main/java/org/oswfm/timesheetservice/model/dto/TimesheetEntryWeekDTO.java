package org.oswfm.timesheetservice.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing one week of hours for a timesheet entry.
 * Maps to the frontend Weeks interface:
 * { week, sunHours, monHours, tueHours, wedHours, thuHours, friHours, satHours }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryWeekDTO {

    private Integer week;

    private BigDecimal sunHours;

    private BigDecimal monHours;

    private BigDecimal tueHours;

    private BigDecimal wedHours;

    private BigDecimal thuHours;

    private BigDecimal friHours;

    private BigDecimal satHours;
}

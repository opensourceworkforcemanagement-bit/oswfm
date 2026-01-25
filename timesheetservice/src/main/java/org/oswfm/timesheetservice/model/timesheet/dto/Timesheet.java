package org.oswfm.timesheetservice.model.timesheet.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @SuperBuilder
// // @EqualsAndHashCode(callSuper = true)
// @NoArgsConstructor
// @AllArgsConstructor
public class Timesheet{

    private Integer  timesheetId;
    private Integer  employeeId;
    private Integer  payPeriodId;
    private List <TimesheetEntry> timesheetEntries;
    private List <TimesheetEntryInOut> timesheetEntriesInOut;
}
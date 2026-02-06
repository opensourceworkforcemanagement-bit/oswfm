package org.oswfm.timesheetservice.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryMinutesRequestDTO {

    //@NotNull(message = "Timesheet entries ID is required")
    private Long timesheetEntriesId;

    @NotNull(message = "Minutes is required")
    private BigDecimal minutes;

    @Size(max = 1, message = "Day of week must be a single character")
    private String dayOfWeek;

    private Date date;
}

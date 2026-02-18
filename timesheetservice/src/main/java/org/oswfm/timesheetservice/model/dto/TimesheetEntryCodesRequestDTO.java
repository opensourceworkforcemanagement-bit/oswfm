package org.oswfm.timesheetservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCodesRequestDTO {

    @NotNull(message = "Timesheet entries ID is required")
    private Integer timesheetEntriesId;

    @NotNull(message = "Workforce codes ID is required")
    private Integer workforceCodesId;
}

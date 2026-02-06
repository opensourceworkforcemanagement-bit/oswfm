package org.oswfm.timesheetservice.model.dto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCreateDTO {

    private WorkforceCodeRefDTO workforceCode;

    private WorkforceCodeRefDTO accountCode;

    @Valid
    private List<TimesheetEntryWeekDTO> weeks;
}

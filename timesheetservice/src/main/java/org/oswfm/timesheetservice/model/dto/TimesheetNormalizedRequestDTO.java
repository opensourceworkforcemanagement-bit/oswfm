package org.oswfm.timesheetservice.model.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNormalizedRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Pay period ID is required")
    private Integer payPeriodId;

    private Integer timesheetTypeId;

    private Integer status;

    @Valid
    private List<TimesheetEntryCreateDTO> timesheetEntries;

    private List<TimesheetEntriesNormalizedRequestDTO> timesheetEntriesNormalized;
}

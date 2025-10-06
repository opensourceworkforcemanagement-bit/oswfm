package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    private Integer payPeriodId;

}

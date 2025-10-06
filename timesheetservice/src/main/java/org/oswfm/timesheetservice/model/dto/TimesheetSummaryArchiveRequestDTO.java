package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetSummaryArchiveRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    private Integer payPeriodId;

    private BigDecimal totalHours;

    private Integer status;

}

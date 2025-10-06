package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetSummaryResponseDTO {

    private Integer timesheetSummaryId;

    private Integer employeeId;

    private Integer payPeriodId;

    private BigDecimal totalHours;

    private Integer operationTypeId;

}

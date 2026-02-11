package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetSummaryResponseDTO {

    private Integer  timesheetSummaryId;

    private Integer  employeeId;

    private Integer  payPeriodId;

    private BigDecimal totalHours;

    private Integer  operationTypeId;

    // Enriched fields

    private String employeeName;

    private LocalDate payPeriodStartDate;

    private LocalDate payPeriodEndDate;

    private Integer year;

    private Integer periodNumber;

    private String operationTypeName;

}

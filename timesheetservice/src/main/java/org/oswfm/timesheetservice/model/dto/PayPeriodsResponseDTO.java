package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPeriodsResponseDTO {

    private Integer payPeriodId;

    private Integer payPeriodTypeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer year;

    private Integer periodNumber;

}

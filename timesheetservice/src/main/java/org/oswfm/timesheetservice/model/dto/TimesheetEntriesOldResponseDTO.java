package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesOldResponseDTO {

    private Integer timesheeId;

    private Integer employeeId;

    private Integer payPeriodId;

    private Integer workCodeId;

    private Integer accountCodeId;

    private BigDecimal su1Hours;

    private BigDecimal m1Hours;

    private BigDecimal t1Hours;

    private BigDecimal w1Hours;

    private BigDecimal th1Hours;

    private BigDecimal f1Hours;

    private BigDecimal sa1Hours;

    private BigDecimal su2Hours;

    private BigDecimal m2Hours;

    private BigDecimal t2Hours;

    private BigDecimal w2Hours;

    private BigDecimal th2Hours;

    private BigDecimal f2Hours;

    private BigDecimal sa2Hours;

}

package org.oswfm.timesheetservice.model.timesheet.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
public class TimesheetEntry{
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
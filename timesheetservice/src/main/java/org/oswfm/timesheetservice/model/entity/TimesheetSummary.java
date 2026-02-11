package org.oswfm.timesheetservice.model.entity;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timesheet_normalized_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_summary_id")
    private Integer  timesheetSummaryId;

    @Column(name = "employee_id", nullable = false)
    private Integer  employeeId;

    @Column(name = "pay_period_id", nullable = false)
    private Integer  payPeriodId;

    @Column(name = "total_hours")
    private BigDecimal totalHours;

    @Column(name = "operation_type_id")
    private Integer  operationTypeId;

}

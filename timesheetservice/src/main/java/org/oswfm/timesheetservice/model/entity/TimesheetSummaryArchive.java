package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "timesheet_summary_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetSummaryArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_summary_id")
    private Integer timesheetSummaryId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "pay_period_id", nullable = false)
    private Integer payPeriodId;

    @Column(name = "total_hours")
    private BigDecimal totalHours;

    private Integer status;

}

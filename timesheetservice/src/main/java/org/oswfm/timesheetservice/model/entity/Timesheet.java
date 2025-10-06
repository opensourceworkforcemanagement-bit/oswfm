package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "timesheet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_id")
    private Integer timesheetId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "pay_period_id", nullable = false)
    private Integer payPeriodId;

}

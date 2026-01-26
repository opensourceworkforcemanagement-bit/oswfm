package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "pay_periods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPeriods {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pay_period_id")
    private Integer payPeriodId;

    @Column(name = "pay_period_type_id", nullable = false)
    private Integer payPeriodTypeId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber;

}

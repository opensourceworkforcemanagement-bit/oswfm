package org.oswfm.timesheetservice.model.entity.timesheetstrategynormalized;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "timesheet_normalized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNormalized {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_normalized_id")
    private Integer timesheetId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "pay_period_id", nullable = false)
    private Integer payPeriodId;

    @Column(name = "timesheet_type_id", nullable = false)
    private Integer timesheetTypeId;

    @Column(name = "status")
    private Short status;

    @OneToOne(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "timesheet_entries_id")
    @ToString.Exclude
    private TimesheetEntry timesheetEntry;
}
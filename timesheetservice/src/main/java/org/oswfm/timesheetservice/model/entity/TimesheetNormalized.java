package org.oswfm.timesheetservice.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timesheet_normalized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNormalized {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_normalized_id")
    private Integer timesheetNormalizedId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "pay_period_id", nullable = false)
    private Integer payPeriodId;

    @Column(name = "timesheet_type_id", nullable = false)
    private Integer timesheetTypeId;

    @Column(name = "status", columnDefinition = "int2")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_period_id", referencedColumnName = "pay_period_id", insertable = false, updatable = false)
    private PayPeriods payPeriod;

    @OneToMany(mappedBy = "timesheetNormalized", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimesheetEntriesNormalized> timesheetEntries;
}

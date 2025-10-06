package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "timesheet_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_entries_id")
    private Integer timesheetEntriesId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    @Column(name = "work_code_id", nullable = false)
    private Integer workCodeId;

    @Column(name = "account_code_id", nullable = false)
    private Integer accountCodeId;

    @Column(name = "su1_hours")
    private BigDecimal su1Hours;

    @Column(name = "m1_hours")
    private BigDecimal m1Hours;

    @Column(name = "t1_hours")
    private BigDecimal t1Hours;

    @Column(name = "w1_hours")
    private BigDecimal w1Hours;

    @Column(name = "th1_hours")
    private BigDecimal th1Hours;

    @Column(name = "f1_hours")
    private BigDecimal f1Hours;

    @Column(name = "sa1_hours")
    private BigDecimal sa1Hours;

    @Column(name = "su2_hours")
    private BigDecimal su2Hours;

    @Column(name = "m2_hours")
    private BigDecimal m2Hours;

    @Column(name = "t2_hours")
    private BigDecimal t2Hours;

    @Column(name = "w2_hours")
    private BigDecimal w2Hours;

    @Column(name = "th2_hours")
    private BigDecimal th2Hours;

    @Column(name = "f2_hours")
    private BigDecimal f2Hours;

    @Column(name = "sa2_hours")
    private BigDecimal sa2Hours;

}

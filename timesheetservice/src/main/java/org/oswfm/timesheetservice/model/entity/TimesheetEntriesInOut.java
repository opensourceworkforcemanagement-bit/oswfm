package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "timesheet_entries_in_out")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesInOut {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timeshee_id")
    private Integer timesheeId;

    @Column(name = "su1_in_time")
    private String su1InTime;

    @Column(name = "m1_in_time")
    private String m1InTime;

    @Column(name = "t1_in_time")
    private String t1InTime;

    @Column(name = "w1_in_time")
    private String w1InTime;

    @Column(name = "th1_in_time")
    private String th1InTime;

    @Column(name = "f1_in_time")
    private String f1InTime;

    @Column(name = "sa1_in_time")
    private String sa1InTime;

    @Column(name = "su2_in_time")
    private String su2InTime;

    @Column(name = "m2_in_time")
    private String m2InTime;

    @Column(name = "t2_in_time")
    private String t2InTime;

    @Column(name = "w2_in_time")
    private String w2InTime;

    @Column(name = "th2_in_time")
    private String th2InTime;

    @Column(name = "f2_in_time")
    private String f2InTime;

    @Column(name = "su1_out_time")
    private String su1OutTime;

    @Column(name = "m1_out_time")
    private String m1OutTime;

    @Column(name = "t1_out_time")
    private String t1OutTime;

    @Column(name = "w1_out_time")
    private String w1OutTime;

    @Column(name = "th1_out_time")
    private String th1OutTime;

    @Column(name = "f1_out_time")
    private String f1OutTime;

    @Column(name = "sa1_out_time")
    private String sa1OutTime;

    @Column(name = "su2_out_time")
    private String su2OutTime;

    @Column(name = "m2_out_time")
    private String m2OutTime;

    @Column(name = "t2_out_time")
    private String t2OutTime;

    @Column(name = "w2_out_time")
    private String w2OutTime;

    @Column(name = "th2_out_time")
    private String th2OutTime;

    @Column(name = "f2_out_time")
    private String f2OutTime;

    @Column(name = "sa2_out_time")
    private String sa2OutTime;

}

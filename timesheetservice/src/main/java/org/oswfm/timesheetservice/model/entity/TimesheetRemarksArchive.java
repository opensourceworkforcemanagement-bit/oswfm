package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "timesheet_remarks_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRemarksArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_remarks_id")
    private Integer timesheetRemarksId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    private String remarks;

    @Column(name = "remarks_order")
    private Integer remarksOrder;

    @Column(name = "created_at")
    private LocalTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

}

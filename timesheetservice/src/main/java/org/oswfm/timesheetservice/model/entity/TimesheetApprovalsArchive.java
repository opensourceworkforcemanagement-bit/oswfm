package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "timesheet_approvals_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_approval_id")
    private Integer timesheetApprovalId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    @Column(name = "approver_id", nullable = false)
    private Integer approverId;

    @Column(name = "approval_status")
    private Integer approvalStatus;

    @Column(name = "approval_date")
    private LocalTime approvalDate;

    private String comments;

}

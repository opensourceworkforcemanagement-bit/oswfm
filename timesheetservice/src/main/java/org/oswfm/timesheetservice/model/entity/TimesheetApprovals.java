package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "timesheet_approvals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovals {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_approval_id")
    private Integer timesheetApprovalId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer timesheetId;

    @Column(name = "approver_id", nullable = false)
    private Integer approverId;

    @Column(name = "operation_type_id")
    private Integer operationTypeId;

    @Column(name = "approval_date")
    private LocalTime approvalDate;

    private String comments;

}

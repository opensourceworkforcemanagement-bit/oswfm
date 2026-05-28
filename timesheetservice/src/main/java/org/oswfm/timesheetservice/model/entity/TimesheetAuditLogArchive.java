package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "timesheet_audit_log_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_audit_log_id")
    private Integer  timesheetAuditLogId;

    @Column(name = "timesheet_id", nullable = false)
    private Integer  timesheetId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Integer  createdBy;

    @Column(name = "operation_type_id", nullable = false)
    private String operationTypeId;

}

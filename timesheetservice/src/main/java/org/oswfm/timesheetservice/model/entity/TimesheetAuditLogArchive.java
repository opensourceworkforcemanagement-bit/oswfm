package org.oswfm.timesheetservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "timesheet_audit_log_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "timesheet_audit_log_id")
    private Integer timesheetAuditLogId;

    @Column(name = "timeshee_id", nullable = false)
    private Integer timesheeId;

    @Column(name = "created_at")
    private LocalTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "operation_type_id", nullable = false)
    private String operationTypeId;

}

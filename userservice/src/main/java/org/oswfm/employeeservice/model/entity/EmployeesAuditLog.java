package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "employees_audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_audit_log_id")
    private Integer employeeAuditLogId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private String action;

    @Column(name = "action_timestamp")
    private LocalTime actionTimestamp;

    @Column(name = "action_by")
    private Integer actionBy;

}

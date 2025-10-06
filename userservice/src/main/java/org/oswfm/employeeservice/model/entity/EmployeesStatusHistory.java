package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "employees_status_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_status_history_id")
    private Integer employeeStatusHistoryId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private Integer status;

    @Column(name = "changed_at")
    private LocalTime changedAt;

    @Column(name = "changed_by_employee_id")
    private Integer changedByEmployeeId;

}

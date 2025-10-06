package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_ssn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesSsn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_ssn_id")
    private Integer employeeSsnId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private String ssn;

}

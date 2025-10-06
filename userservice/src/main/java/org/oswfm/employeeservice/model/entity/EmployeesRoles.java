package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_role_id")
    private Integer employeeRoleId;

    @Column(nullable = false)
    private String role;

    private String description;

}

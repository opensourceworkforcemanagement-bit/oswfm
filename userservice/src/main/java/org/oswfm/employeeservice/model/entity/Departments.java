package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(nullable = false)
    private String department;

    private String description;

    @Column(nullable = false)
    private Integer status;

}

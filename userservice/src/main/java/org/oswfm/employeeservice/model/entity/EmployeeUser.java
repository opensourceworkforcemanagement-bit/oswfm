package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_user_id")
    private Integer  employeeUserId;

    @Column(name = "employee_id", nullable = false)
    private Integer  employeeId;

    @Column(name = "user_id", nullable = false)
    private Integer  userId;

}

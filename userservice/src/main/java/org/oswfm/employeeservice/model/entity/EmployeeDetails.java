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
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer  employeeId;

    @Column(name = "employee_identifier", nullable = false)
    private String employeeIdentifier;

    //@TODO: Add
    @Column(name = "user_id")
    private  Integer userId;

    //@TODO: remove
    @Column(name = "first_name")
    private String firstName;
    //@TODO: remove
    @Column(name = "middle_name")
    private String middleName;
    //@TODO: remove
    @Column(name = "last_name")
    private String lastName;
    //@TODO: remove
    private Integer  status;
    //@TODO: remove
    @Column(name = "user_name", nullable = false)
    private String userName;

}

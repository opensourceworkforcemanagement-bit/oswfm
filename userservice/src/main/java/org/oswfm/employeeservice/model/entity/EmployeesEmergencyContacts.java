package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_emergency_contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "emergency_contact_id")
    private Integer emergencyContactId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    private String relationship;

}

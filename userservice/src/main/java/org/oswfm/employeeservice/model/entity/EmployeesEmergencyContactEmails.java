package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_emergency_contact_emails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactEmails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "emergency_contact_email_id")
    private Integer emergencyContactEmailId;

    @Column(name = "emergency_contact_id", nullable = false)
    private Integer emergencyContactId;

    @Column(name = "email_address_id", nullable = false)
    private Integer emailAddressId;

}

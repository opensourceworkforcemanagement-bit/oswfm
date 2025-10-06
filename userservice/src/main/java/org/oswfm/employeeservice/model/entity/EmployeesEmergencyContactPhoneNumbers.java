package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_emergency_contact_phone_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactPhoneNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "emergency_contact_info_id")
    private Integer emergencyContactInfoId;

    @Column(name = "emergency_contact_id", nullable = false)
    private Integer emergencyContactId;

    @Column(name = "phone_number_id", nullable = false)
    private Integer phoneNumberId;

}

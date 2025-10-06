package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "phone_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "phone_number_id")
    private Integer phoneNumberId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer type;

    @Column(name = "is_primary")
    private Boolean isPrimary;

}

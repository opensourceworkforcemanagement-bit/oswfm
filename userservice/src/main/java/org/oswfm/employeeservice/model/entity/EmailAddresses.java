package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "email_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "email_address_id")
    private Integer emailAddressId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer type;

    @Column(name = "is_primary")
    private Boolean isPrimary;

}

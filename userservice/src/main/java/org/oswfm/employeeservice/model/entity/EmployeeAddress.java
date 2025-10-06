package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employee_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

}

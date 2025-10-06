package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_preference_id")
    private Integer employeePreferenceId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "preference_key", nullable = false)
    private String preferenceKey;

    @Column(name = "preference_description")
    private String preferenceDescription;

}

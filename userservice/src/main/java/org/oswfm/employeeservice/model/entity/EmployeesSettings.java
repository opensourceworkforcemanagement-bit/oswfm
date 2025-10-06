package org.oswfm.employeeservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_setting_id")
    private Integer employeeSettingId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "setting_key", nullable = false)
    private String settingKey;

    @Column(name = "setting_description")
    private String settingDescription;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesSettingsResponseDTO {

    private Integer employeeSettingId;

    private Integer employeeId;

    private String settingKey;

    private String settingDescription;

}

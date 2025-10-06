package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesPreferencesResponseDTO {

    private Integer employeePreferenceId;

    private Integer employeeId;

    private String preferenceKey;

    private String preferenceDescription;

}

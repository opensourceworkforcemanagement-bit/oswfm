package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesPreferencesRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    @NotBlank
    private String preferenceKey;

    private String preferenceDescription;

}

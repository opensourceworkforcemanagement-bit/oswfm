package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesRequestDTO {

    @NotNull
    @NotBlank
    private String employeeIdentifier;

    private String firstName;

    private String middleName;

    private String lastName;

    private Integer status;

    @NotNull
    @NotBlank
    private String userName;

}

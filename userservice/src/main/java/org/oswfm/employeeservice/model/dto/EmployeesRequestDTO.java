package org.oswfm.employeeservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesRequestDTO {

    @NotNull
    @NotBlank
    private String employeeIdentifier;

    @NotNull
    @NotBlank
    private String firstName;

    private String middleName;

    @NotNull
    @NotBlank
    private String lastName;

    private Integer  status;

    @NotNull
    @NotBlank
    private String userName;

}

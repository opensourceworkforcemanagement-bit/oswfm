package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactsRequestDTO {

    @NotNull
    private Integer employeeId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String relationship;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesResponseDTO {

    private Integer employeeId;

    private String employeeIdentifier;

    private String firstName;

    private String middleName;

    private String lastName;

    private Integer status;

    private String userName;

}

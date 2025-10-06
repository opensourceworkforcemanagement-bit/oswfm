package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactsResponseDTO {

    private Integer emergencyContactId;

    private Integer employeeId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String relationship;

}

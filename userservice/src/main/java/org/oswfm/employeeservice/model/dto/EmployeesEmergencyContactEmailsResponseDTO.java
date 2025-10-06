package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactEmailsResponseDTO {

    private Integer emergencyContactEmailId;

    private Integer emergencyContactId;

    private Integer emailAddressId;

}

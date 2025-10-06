package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesEmergencyContactPhoneNumbersResponseDTO {

    private Integer emergencyContactInfoId;

    private Integer emergencyContactId;

    private Integer phoneNumberId;

}

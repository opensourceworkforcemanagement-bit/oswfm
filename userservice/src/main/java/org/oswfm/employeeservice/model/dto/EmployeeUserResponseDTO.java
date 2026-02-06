package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUserResponseDTO {

    private Integer  employeeUserId;

    private Integer  employeeId;

    private Integer  userId;

}

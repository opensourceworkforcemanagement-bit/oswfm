package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesSsnResponseDTO {

    private Integer employeeSsnId;

    private Integer employeeId;

    private String ssn;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentsResponseDTO {

    private Integer departmentId;

    private String department;

    private String description;

    private Integer status;

}

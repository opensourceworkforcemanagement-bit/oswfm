package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesStatusHistoryResponseDTO {

    private Integer employeeStatusHistoryId;

    private Integer employeeId;

    private Integer status;

    private LocalTime changedAt;

    private Integer changedByEmployeeId;

}

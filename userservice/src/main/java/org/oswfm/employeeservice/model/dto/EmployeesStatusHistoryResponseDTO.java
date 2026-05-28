package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesStatusHistoryResponseDTO {

    private Integer  employeeStatusHistoryId;

    private Integer  employeeId;

    private Integer  status;

    private LocalDateTime changedAt;

    private Integer  changedByEmployeeId;

}

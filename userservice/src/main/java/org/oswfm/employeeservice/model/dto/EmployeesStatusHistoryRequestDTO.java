package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesStatusHistoryRequestDTO {

    @NotNull
    private Integer  employeeId;

    @NotNull
    private Integer  status;

    private LocalDateTime changedAt;

    private Integer  changedByEmployeeId;

}

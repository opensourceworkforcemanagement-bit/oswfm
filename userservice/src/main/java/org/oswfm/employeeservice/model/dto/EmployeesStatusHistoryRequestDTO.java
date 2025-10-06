package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesStatusHistoryRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    private Integer status;

    private LocalTime changedAt;

    private Integer changedByEmployeeId;

}

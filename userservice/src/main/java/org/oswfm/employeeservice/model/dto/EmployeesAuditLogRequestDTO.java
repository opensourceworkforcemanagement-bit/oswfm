package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesAuditLogRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    @NotBlank
    private String action;

    private LocalTime actionTimestamp;

    private Integer actionBy;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesAuditLogRequestDTO {

    @NotNull
    private Integer  employeeId;

    @NotNull
    @NotBlank
    private String action;

    private LocalDateTime actionTimestamp;

    private Integer  actionBy;

}

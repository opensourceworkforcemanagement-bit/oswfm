package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesAuditLogResponseDTO {

    private Integer employeeAuditLogId;

    private Integer employeeId;

    private String action;

    private LocalTime actionTimestamp;

    private Integer actionBy;

}

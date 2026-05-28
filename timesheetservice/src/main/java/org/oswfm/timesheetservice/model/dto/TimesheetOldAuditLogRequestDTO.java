package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetOldAuditLogRequestDTO {

    @NotNull
    private Integer  timesheetId;

    private LocalDateTime createdAt;

    private Integer  createdBy;

}

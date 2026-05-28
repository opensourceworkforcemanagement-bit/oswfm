package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogArchiveRequestDTO {

    @NotNull
    private Integer  timesheetId;

    private LocalDateTime createdAt;

    private Integer  createdBy;

    @NotNull
    @NotBlank
    private String operationTypeId;

}

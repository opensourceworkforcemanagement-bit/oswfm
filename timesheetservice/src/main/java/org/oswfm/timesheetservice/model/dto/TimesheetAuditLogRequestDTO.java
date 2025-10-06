package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogRequestDTO {

    @NotNull
    private Integer timesheeId;

    private LocalTime createdAt;

    private Integer createdBy;

}

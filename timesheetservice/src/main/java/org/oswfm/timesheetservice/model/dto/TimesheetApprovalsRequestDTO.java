package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsRequestDTO {

    @NotNull
    private Integer  timesheetId;

    @NotNull
    private Integer  approverId;

    private Integer  operationTypeId;

    private LocalDateTime approvalDate;

    private String comments;

}

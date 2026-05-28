package org.oswfm.timesheetservice.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsArchiveRequestDTO {

    @NotNull
    private Integer  timesheetId;

    @NotNull
    private Integer  approverId;

    private Integer  approvalStatus;

    private LocalDateTime approvalDate;

    private String comments;

}

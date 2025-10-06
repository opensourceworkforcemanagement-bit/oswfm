package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsArchiveRequestDTO {

    @NotNull
    private Integer timesheetId;

    @NotNull
    private Integer approverId;

    private Integer approvalStatus;

    private LocalTime approvalDate;

    private String comments;

}

package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsResponseDTO {

    private Integer  timesheetApprovalId;

    private Integer  timesheetId;

    private Integer  approverId;

    private Integer  operationTypeId;

    private LocalDateTime approvalDate;

    private String comments;

}

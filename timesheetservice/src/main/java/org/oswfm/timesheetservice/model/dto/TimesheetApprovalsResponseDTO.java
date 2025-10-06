package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetApprovalsResponseDTO {

    private Integer timesheetApprovalId;

    private Integer timesheetId;

    private Integer approverId;

    private Integer operationTypeId;

    private LocalTime approvalDate;

    private String comments;

}

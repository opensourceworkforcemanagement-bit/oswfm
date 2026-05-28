package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogArchiveResponseDTO {

    private Integer  timesheetAuditLogId;

    private Integer  timesheetId;

    private LocalDateTime createdAt;

    private Integer  createdBy;

    private String operationTypeId;

}

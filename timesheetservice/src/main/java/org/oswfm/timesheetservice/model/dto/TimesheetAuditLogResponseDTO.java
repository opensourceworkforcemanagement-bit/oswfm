package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetAuditLogResponseDTO {

    private Integer timesheetAuditLogId;

    private Integer timesheeId;

    private LocalTime createdAt;

    private Integer createdBy;

}

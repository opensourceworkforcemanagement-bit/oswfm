package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRemarksOldResponseDTO {

    private Integer timesheetRemarksId;

    private Integer timesheetId;

    private String remarks;

    private Integer remarksOrder;

    private LocalTime createdAt;

    private Integer createdBy;

}

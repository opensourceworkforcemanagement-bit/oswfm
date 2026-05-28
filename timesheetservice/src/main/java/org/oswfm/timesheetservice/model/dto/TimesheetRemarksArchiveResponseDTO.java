package org.oswfm.timesheetservice.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRemarksArchiveResponseDTO {

    private Integer  timesheetRemarksId;

    private Integer  timesheetId;

    private String remarks;

    private Integer  remarksOrder;

    private LocalDateTime createdAt;

    private Integer  createdBy;

}

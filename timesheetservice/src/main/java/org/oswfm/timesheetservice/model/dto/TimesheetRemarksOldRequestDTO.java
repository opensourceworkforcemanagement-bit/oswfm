package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRemarksOldRequestDTO {

    @NotNull
    private Integer timesheetId;

    private String remarks;

    private Integer remarksOrder;

    private LocalTime createdAt;

    private Integer createdBy;

}

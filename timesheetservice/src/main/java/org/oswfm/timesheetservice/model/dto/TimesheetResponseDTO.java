package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetResponseDTO {

    private Integer timesheetId;

    private Integer employeeId;

    private Integer payPeriodId;

}

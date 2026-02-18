package org.oswfm.timesheetservice.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNormalizedResponseDTO {

    private Integer timesheetNormalizedId;

    private Integer employeeId;

    private Integer payPeriodId;

    private Integer timesheetTypeId;

    private Integer status;

    private List<TimesheetEntriesNormalizedResponseDTO> timesheetEntries;
}

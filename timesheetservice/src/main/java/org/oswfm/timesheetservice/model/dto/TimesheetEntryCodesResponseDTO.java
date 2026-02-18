package org.oswfm.timesheetservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryCodesResponseDTO {

    private Integer timesheetEntriesId;

    private Integer workforceCodesId;

    private WorkforceCodesResponseDTO workforceCode;
}

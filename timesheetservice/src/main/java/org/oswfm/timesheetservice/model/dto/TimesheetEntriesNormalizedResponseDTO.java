package org.oswfm.timesheetservice.model.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesNormalizedResponseDTO {

    private Integer timesheetEntriesId;

    private Integer timesheetNormalizedId;

    private Set<WorkforceCodesResponseDTO> workforceCodes;

    private List<TimesheetEntryMinutesResponseDTO> entryMinutes;
}

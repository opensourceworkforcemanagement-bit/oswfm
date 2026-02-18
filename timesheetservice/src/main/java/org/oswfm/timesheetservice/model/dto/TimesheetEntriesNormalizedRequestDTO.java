package org.oswfm.timesheetservice.model.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesNormalizedRequestDTO {

    //@NotNull(message = "Timesheet normalized ID is required")
    private Integer timesheetNormalizedId;

    private Set<Integer> workforceCodeIds; //Primary key Ids of associated workforce codes

    private List<TimesheetEntryMinutesRequestDTO> entryMinutes;
}

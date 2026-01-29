package org.oswfm.timesheetservice.model.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesNormalizedRequestDTO {

    @NotNull(message = "Timesheet normalized ID is required")
    private Long timesheetNormalizedId;

    private Set<Long> workforceCodeIds;
}

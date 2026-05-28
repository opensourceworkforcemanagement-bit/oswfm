package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesCommentsRequestDTO {

    @NotNull
    private Integer  timesheetId;

    @NotNull
    private Integer  entryDay;

    private String comments;

}

package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesCommentsOldRequestDTO {

    @NotNull
    private Integer timesheeId;

    @NotNull
    private Integer entryDay;

    private String comments;

}

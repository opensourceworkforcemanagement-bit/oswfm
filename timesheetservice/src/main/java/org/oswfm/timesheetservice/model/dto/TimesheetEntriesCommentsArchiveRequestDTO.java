package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesCommentsArchiveRequestDTO {

    @NotNull
    private Integer timesheeId;

    @NotNull
    private Integer entryDay;

    private String comments;

}

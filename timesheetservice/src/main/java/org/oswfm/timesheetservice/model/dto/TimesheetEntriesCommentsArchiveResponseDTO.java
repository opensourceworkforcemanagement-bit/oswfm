package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntriesCommentsArchiveResponseDTO {

    private Integer timesheetEntriesCommentsId;

    private Integer timesheeId;

    private Integer entryDay;

    private String comments;

}

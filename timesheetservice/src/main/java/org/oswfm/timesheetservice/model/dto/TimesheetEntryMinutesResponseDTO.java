package org.oswfm.timesheetservice.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryMinutesResponseDTO {

    private Long id;

    private Long timesheetEntriesId;

    private BigDecimal minutes;

    private String dayOfWeek;

    private Date date;
}

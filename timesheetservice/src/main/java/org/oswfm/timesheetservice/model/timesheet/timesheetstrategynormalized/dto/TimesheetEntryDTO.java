package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryDTO {
    private Integer timesheetEntryId;
    private Integer timesheetId;
    private Map<String, CodeDTO> codes; // code_type -> CodeDTO
    private Map<LocalDate, BigDecimal> minutes; // date -> minutes
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

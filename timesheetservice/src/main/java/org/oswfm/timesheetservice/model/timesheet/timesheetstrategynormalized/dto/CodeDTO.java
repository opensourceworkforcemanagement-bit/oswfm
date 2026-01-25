package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic DTO for code information
 * Used for internal mapping and processing
 * Maps to timesheet_entry_codes join table structure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDTO {

    private Long timesheetEntryId;

    private Long workforceCodeId;
}

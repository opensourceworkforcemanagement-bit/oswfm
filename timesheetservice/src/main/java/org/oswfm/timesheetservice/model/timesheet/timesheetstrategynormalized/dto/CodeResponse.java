package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oswfm.timesheetservice.model.dto.WorkforceCodesResponseDTO;

/**
 * Response DTO for timesheet entry codes
 * Maps from TimesheetEntryCode entity with full workforce code details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeResponse {

    private Integer timesheetEntryId;

    private WorkforceCodesResponseDTO workforceCode;
}

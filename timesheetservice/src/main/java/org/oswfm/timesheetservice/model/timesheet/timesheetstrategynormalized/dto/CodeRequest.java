package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for associating workforce codes with timesheet entries
 * Maps to timesheet_entry_codes join table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequest {

    @NotNull(message = "Workforce code ID is required")
    private Long workforceCodeId;
}

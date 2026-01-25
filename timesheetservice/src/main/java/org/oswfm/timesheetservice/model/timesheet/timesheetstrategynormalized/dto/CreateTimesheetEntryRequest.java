package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimesheetEntryRequest {
    
    @NotNull(message = "Timesheet ID is required")
    private Integer timesheetId;

    @NotEmpty(message = "At least one code is required")
    private Map<String, CodeRequest> codes;

    @NotEmpty(message = "At least one minute entry is required")
    private Map<LocalDate, @DecimalMin(value = "0.0") @DecimalMax(value = "1440.0") BigDecimal> minutes;
}

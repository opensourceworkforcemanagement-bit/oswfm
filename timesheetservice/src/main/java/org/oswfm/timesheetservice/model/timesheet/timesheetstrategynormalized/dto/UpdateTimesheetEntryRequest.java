package org.oswfm.timesheetservice.model.timesheet.timesheetstrategynormalized.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTimesheetEntryRequest {
    
    private Map<String, CodeRequest> codes;

    private Map<LocalDate, @DecimalMin(value = "0.0") @DecimalMax(value = "1444.0") BigDecimal> minutes;
}

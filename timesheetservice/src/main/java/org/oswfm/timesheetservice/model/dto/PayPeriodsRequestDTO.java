package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPeriodsRequestDTO {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}

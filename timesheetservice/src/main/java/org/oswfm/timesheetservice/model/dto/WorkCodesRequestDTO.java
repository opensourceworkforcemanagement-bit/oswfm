package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodesRequestDTO {

    @NotNull
    @NotBlank
    private String workCode;

    private String description;

    private Integer status;

}

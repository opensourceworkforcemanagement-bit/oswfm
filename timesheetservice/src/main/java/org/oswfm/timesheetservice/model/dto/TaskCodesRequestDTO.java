package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCodesRequestDTO {

    @NotNull
    @NotBlank
    private String taskCode;

    private String description;

    private Integer status;

}

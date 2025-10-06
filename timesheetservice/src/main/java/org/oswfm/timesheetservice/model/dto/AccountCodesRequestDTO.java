package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCodesRequestDTO {

    @NotNull
    @NotBlank
    private String accountCode;

    private String description;

    private Integer status;

}

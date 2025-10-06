package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentsRequestDTO {

    @NotNull
    @NotBlank
    private String department;

    private String description;

    @NotNull
    private Integer status;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsRequestDTO {

    @NotNull
    @NotBlank
    private String project;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Integer status;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsResponseDTO {

    private Integer projectId;

    private String project;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer status;

}

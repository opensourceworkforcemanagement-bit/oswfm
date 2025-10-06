package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCodesResponseDTO {

    private Integer taskCodeId;

    private String taskCode;

    private String description;

    private Integer status;

}

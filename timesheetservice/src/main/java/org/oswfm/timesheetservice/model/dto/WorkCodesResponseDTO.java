package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodesResponseDTO {

    private Integer workCodeId;

    private String workCode;

    private String description;

    private Integer status;

}

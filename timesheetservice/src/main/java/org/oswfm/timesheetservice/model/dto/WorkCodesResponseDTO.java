package org.oswfm.timesheetservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodesResponseDTO {

    private Integer workCodeId;

    private String workCode;

    private String description;

    private Integer status;

    private String prefix;

    private String suffix;

}

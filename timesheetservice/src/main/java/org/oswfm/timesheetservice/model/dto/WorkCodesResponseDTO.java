package org.oswfm.timesheetservice.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodesResponseDTO {

    private Integer  work_code_id;

    private String short_work_code;

    private String long_work_code;

    private String description;

    private Integer  status;

    private String prefix;

    private String suffix;

    private Date effective_date;

    private Date expiration_date;

}

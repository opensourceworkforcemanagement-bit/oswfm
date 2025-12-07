package org.oswfm.timesheetservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodesRequestDTO {

    @NotNull
    @NotBlank
    private String short_work_code;
    
    @NotNull
    @NotBlank   
    private String long_work_code;
    
    private String prefix;

    private String suffix;

    private String description;

    private Integer  status;   

}

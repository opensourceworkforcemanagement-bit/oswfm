package org.oswfm.timesheetservice.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkforceCodesResponseDTO {

    private Long id;

    private Integer codeTypeId;

    private Long codeId;

    private String prefix;

    private String suffix;

    private String shortCodeValue;

    private String longCodeValue;

    private String description;

    private Integer status;

    private Date effectiveDate;

    private Date expirationDate;
}

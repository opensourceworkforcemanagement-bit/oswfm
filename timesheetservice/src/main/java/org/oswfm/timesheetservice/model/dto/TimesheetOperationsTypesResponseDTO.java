package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetOperationsTypesResponseDTO {

    private Integer operationTypeId;

    private String operationTypeName;

    private String operationDescription;

}

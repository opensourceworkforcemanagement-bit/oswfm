package org.oswfm.timesheetservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeTypesResponseDTO {

    private Integer codeTypeId;

    private String codeTypeName;

    private String description;
}

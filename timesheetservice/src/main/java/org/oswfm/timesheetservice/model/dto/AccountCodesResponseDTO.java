package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCodesResponseDTO {

    private Integer accountCodeId;

    private String accountCode;

    private String description;

    private Integer status;

}

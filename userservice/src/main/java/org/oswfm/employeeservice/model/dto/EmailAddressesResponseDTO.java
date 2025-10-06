package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressesResponseDTO {

    private Integer emailAddressId;

    private Integer employeeId;

    private String email;

    private Integer type;

    private Boolean isPrimary;

}

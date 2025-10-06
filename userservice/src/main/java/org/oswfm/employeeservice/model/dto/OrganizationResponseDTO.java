package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDTO {

    private Integer organizationId;

    private Integer parentOrganizationId;

    private String organization;

    private String description;

    private Integer status;

}

package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissioinsResponseDTO {

    private Integer permissioinsId;

    private Integer permissionTag;

    private String permissionName;

    private String description;

}

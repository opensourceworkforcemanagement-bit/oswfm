package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsResponseDTO {

    private Integer rolePermissionId;

    private String employeeRoleId;

    private Integer permissioinsId;

}

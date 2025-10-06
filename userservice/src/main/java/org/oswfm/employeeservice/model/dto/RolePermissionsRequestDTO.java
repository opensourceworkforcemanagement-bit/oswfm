package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsRequestDTO {

    @NotNull
    @NotBlank
    private String employeeRoleId;

    @NotNull
    private Integer permissioinsId;

}

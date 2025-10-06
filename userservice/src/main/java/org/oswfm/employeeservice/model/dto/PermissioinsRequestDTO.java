package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissioinsRequestDTO {

    @NotNull
    private Integer permissionTag;

    @NotNull
    @NotBlank
    private String permissionName;

    private String description;

}

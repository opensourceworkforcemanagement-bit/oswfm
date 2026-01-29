package org.oswfm.timesheetservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeTypesRequestDTO {

    @NotNull(message = "Code type name is required")
    @NotBlank(message = "Code type name cannot be blank")
    @Size(max = 50, message = "Code type name cannot exceed 50 characters")
    private String codeTypeName;

    private String description;
}

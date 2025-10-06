package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressesRequestDTO {

    @NotNull
    private Integer employeeId;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    private Integer type;

    private Boolean isPrimary;

}

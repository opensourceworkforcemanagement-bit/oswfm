package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressesRequestDTO {

    @NotNull
    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String zipCode;

    @NotNull
    @NotBlank
    private String country;

    private Boolean isPrimary;

}

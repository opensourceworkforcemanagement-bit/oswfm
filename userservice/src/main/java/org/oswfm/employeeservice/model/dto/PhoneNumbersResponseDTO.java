package org.oswfm.employeeservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumbersResponseDTO {

    private Integer phoneNumberId;

    private Integer employeeId;

    private String phoneNumber;

    private Integer type;

    private Boolean isPrimary;

}

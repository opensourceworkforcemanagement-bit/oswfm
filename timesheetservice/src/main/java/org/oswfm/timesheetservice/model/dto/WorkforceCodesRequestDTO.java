package org.oswfm.timesheetservice.model.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkforceCodesRequestDTO {

    @NotNull(message = "Code type is required")
    @NotBlank(message = "Code type cannot be blank")
    @Size(max = 50, message = "Code type cannot exceed 50 characters")
    private String codeType;

    @NotNull(message = "Code ID is required")
    private Long codeId;

    @NotNull(message = "Short code value is required")
    @NotBlank(message = "Short code value cannot be blank")
    @Size(max = 10, message = "Short code value cannot exceed 10 characters")
    private String shortCodeValue;

    @NotNull(message = "Long code value is required")
    @NotBlank(message = "Long code value cannot be blank")
    @Size(max = 255, message = "Long code value cannot exceed 255 characters")
    private String longCodeValue;

    private String description;

    private Integer status;

    private Date effectiveDate;

    private Date expirationDate;
}

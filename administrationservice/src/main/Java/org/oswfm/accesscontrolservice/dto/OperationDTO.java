package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {

    private Integer operationId;

    @NotBlank(message = "Operation name is required")
    private String operationName;

    private String description;
    private OffsetDateTime createdAt;
}

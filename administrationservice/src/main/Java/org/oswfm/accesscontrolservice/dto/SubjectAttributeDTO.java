package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAttributeDTO {

    private Integer subjectAttrId;

    @NotNull(message = "Attribute ID is required")
    private Integer attributeId;

    private String attributeName;

    @NotBlank(message = "Attribute value is required")
    private String attributeValue;

    private OffsetDateTime validFrom;
    private OffsetDateTime validUntil;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

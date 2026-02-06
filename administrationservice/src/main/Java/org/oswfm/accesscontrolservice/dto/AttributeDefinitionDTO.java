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
public class AttributeDefinitionDTO {

    private Integer attributeId;

    @NotBlank(message = "Attribute name is required")
    private String attributeName;

    @NotNull(message = "Attribute category ID is required")
    private Integer attributeCategoryId;
    private String attributeCategoryName;

    @NotNull(message = "Data type ID is required")
    private Integer dataTypeId;
    private String dataTypeName;

    private String description;
    private Boolean isRequired;
    private OffsetDateTime createdAt;
}

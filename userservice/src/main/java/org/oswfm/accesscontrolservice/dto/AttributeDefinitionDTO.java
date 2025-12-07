package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinitionDTO {
    
    private  Integer attributeId;
    
    @NotBlank(message = "Attribute name is required")
    private String attributeName;
    
    @NotBlank(message = "Attribute category is required")
    private String attributeCategory;
    
    @NotBlank(message = "Data type is required")
    private String dataType;
    
    private String description;
    private Boolean isRequired;
    private OffsetDateTime createdAt;
}

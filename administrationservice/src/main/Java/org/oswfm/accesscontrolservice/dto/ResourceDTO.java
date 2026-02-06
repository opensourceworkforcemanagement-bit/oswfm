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
public class ResourceDTO {

    private Integer resourceId;

    @NotNull(message = "Resource type ID is required")
    private Integer resourceTypeId;
    private String resourceTypeName;

    @NotBlank(message = "Resource name is required")
    private String resourceName;

    private String resourceUri;
    private Integer ownerId;
    private String ownerUsername;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

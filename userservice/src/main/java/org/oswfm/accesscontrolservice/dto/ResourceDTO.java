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
public class ResourceDTO {
    
    private  Integer resourceId;
    
    @NotBlank(message = "Resource type is required")
    private String resourceType;
    
    @NotBlank(message = "Resource name is required")
    private String resourceName;
    
    private String resourceUri;
    private  Integer ownerId;
    private String ownerUsername;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

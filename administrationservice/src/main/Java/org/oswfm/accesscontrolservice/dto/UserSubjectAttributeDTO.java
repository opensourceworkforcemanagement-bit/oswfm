package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubjectAttributeDTO {

    private Integer id;

    @NotNull(message = "User ID is required")
    private Integer userId;

    private String username;

    @NotNull(message = "Subject Attribute ID is required")
    private Integer subjectAttrId;

    private String attributeName;
    private String attributeValue;

    private OffsetDateTime createdAt;
}

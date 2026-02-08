package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSubjectAttributeDTO {

    private Integer id;

    @NotNull(message = "Group ID is required")
    private Integer groupId;

    private String groupName;

    @NotNull(message = "Subject Attribute ID is required")
    private Integer subjectAttrId;

    private String attributeName;
    private String attributeValue;

    private OffsetDateTime createdAt;
}

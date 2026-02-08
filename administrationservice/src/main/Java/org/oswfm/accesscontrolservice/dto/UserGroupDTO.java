package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDTO {

    private Integer groupId;

    @NotBlank(message = "Group name is required")
    private String groupName;

    private String description;

    private Integer parentGroupId;
    private String parentGroupName;

    private OffsetDateTime createdAt;
}

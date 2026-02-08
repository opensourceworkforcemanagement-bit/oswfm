package org.oswfm.accesscontrolservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupMembershipDTO {

    private Integer membershipId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    private String username;

    @NotNull(message = "Group ID is required")
    private Integer groupId;

    private String groupName;
}

package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.UserGroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupMembershipRepository extends JpaRepository<UserGroupMembership, Integer> {

    List<UserGroupMembership> findByUser_UserId(Integer userId);

    List<UserGroupMembership> findByGroup_GroupId(Integer groupId);
}

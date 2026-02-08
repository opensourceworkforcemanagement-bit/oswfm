package org.oswfm.accesscontrolservice.repository;

import org.oswfm.accesscontrolservice.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
}

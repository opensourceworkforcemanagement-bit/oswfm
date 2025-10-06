package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Integer> {
    // Add custom query methods here if needed
}

package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRolesRepository extends JpaRepository<EmployeesRoles, Integer> {
    // Add custom query methods here if needed
}

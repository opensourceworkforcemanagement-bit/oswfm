package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesAuditLogRepository extends JpaRepository<EmployeesAuditLog, Integer> {
    // Add custom query methods here if needed
}

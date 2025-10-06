package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesStatusHistoryRepository extends JpaRepository<EmployeesStatusHistory, Integer> {
    // Add custom query methods here if needed
}

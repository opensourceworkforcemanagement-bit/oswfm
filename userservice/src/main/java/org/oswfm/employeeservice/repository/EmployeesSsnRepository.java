package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesSsn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesSsnRepository extends JpaRepository<EmployeesSsn, Integer> {
    // Add custom query methods here if needed
}

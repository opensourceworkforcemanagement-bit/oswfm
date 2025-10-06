package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Integer> {
    // Add custom query methods here if needed
}

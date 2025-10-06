package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactPhoneNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesEmergencyContactPhoneNumbersRepository extends JpaRepository<EmployeesEmergencyContactPhoneNumbers, Integer> {
    // Add custom query methods here if needed
}

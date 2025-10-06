package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesEmergencyContactsRepository extends JpaRepository<EmployeesEmergencyContacts, Integer> {
    // Add custom query methods here if needed
}

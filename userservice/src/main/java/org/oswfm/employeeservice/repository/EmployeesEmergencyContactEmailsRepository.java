package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactEmails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesEmergencyContactEmailsRepository extends JpaRepository<EmployeesEmergencyContactEmails, Integer> {
    // Add custom query methods here if needed
}

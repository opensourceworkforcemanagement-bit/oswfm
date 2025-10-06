package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesPreferencesRepository extends JpaRepository<EmployeesPreferences, Integer> {
    // Add custom query methods here if needed
}

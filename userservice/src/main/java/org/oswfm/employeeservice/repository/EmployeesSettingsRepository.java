package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmployeesSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesSettingsRepository extends JpaRepository<EmployeesSettings, Integer> {
    // Add custom query methods here if needed
}

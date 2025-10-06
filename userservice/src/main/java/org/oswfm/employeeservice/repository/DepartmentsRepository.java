package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Integer> {
    // Add custom query methods here if needed
}

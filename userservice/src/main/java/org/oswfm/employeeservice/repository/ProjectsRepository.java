package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
    // Add custom query methods here if needed
}

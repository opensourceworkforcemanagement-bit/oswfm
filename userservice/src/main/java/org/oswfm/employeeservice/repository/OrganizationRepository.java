package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    // Add custom query methods here if needed
}

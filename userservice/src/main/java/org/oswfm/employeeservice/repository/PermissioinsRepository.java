package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.Permissioins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissioinsRepository extends JpaRepository<Permissioins, Integer> {
    // Add custom query methods here if needed
}

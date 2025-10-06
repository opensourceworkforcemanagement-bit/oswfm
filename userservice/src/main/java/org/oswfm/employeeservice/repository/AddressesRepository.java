package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses, Integer> {
    // Add custom query methods here if needed
}

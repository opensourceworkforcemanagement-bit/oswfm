package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.EmailAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAddressesRepository extends JpaRepository<EmailAddresses, Integer> {
    // Add custom query methods here if needed
}

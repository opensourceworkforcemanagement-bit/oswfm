package org.oswfm.employeeservice.repository;

import org.oswfm.employeeservice.model.entity.PhoneNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumbersRepository extends JpaRepository<PhoneNumbers, Integer> {
    // Add custom query methods here if needed
}

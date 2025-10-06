package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.PayPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayPeriodsRepository extends JpaRepository<PayPeriods, Integer> {
    // Add custom query methods here if needed
}

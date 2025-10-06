package org.oswfm.timesheetservice.repository;

import org.oswfm.timesheetservice.model.entity.AccountCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCodesRepository extends JpaRepository<AccountCodes, Integer> {
    // Add custom query methods here if needed
}

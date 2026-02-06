package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.PolicyActionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyActionTargetRepository extends JpaRepository<PolicyActionTarget, Integer > {
    
    List<PolicyActionTarget> findByPolicy_PolicyId( Integer policyId);
    
    void deleteByPolicy_PolicyId( Integer policyId);
}

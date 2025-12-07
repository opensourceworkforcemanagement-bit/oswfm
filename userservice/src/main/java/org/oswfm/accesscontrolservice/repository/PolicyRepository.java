package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer > {
    
    Optional<Policy> findByPolicyName(String policyName);
    
    List<Policy> findByIsActive(Boolean isActive);
    
    List<Policy> findByPolicyType(String policyType);
    
    @Query("SELECT p FROM Policy p WHERE p.isActive = true ORDER BY p.priority DESC")
    List<Policy> findActivePoliciesOrderedByPriority();
    
    boolean existsByPolicyName(String policyName);
}

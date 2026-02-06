package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.PolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRuleRepository extends JpaRepository<PolicyRule, Integer > {
    
    List<PolicyRule> findByPolicy_PolicyIdOrderByRuleOrder( Integer policyId);
    
    @Query("SELECT pr FROM PolicyRule pr WHERE pr.policy.policyId = :policyId ORDER BY pr.ruleOrder")
    List<PolicyRule> findRulesByPolicyIdOrdered(@Param("policyId")  Integer policyId);
    
    void deleteByPolicy_PolicyId( Integer policyId);
}

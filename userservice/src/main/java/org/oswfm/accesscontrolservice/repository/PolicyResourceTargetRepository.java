package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.UUID;

import org.oswfm.accesscontrolservice.model.entity.PolicyResourceTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyResourceTargetRepository extends JpaRepository<PolicyResourceTarget, Integer > {
    
    List<PolicyResourceTarget> findByPolicy_PolicyId( Integer policyId);
    
    void deleteByPolicy_PolicyId( Integer policyId);
}

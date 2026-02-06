package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.PolicyOperationTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyOperationTargetRepository extends JpaRepository<PolicyOperationTarget, Integer > {

    List<PolicyOperationTarget> findByPolicy_PolicyId( Integer policyId);

    void deleteByPolicy_PolicyId( Integer policyId);
}

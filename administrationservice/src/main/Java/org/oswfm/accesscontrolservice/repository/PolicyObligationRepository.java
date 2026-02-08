package org.oswfm.accesscontrolservice.repository;

import java.util.List;

import org.oswfm.accesscontrolservice.model.entity.PolicyObligation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyObligationRepository extends JpaRepository<PolicyObligation, Integer> {

    List<PolicyObligation> findByPolicy_PolicyId(Integer policyId);

    List<PolicyObligation> findByObligationType_ObligationTypeId(Integer obligationTypeId);

    List<PolicyObligation> findByIsMandatory(Boolean isMandatory);

    @Query("SELECT po FROM PolicyObligation po WHERE po.policy.policyId = :policyId")
    List<PolicyObligation> findObligationsByPolicyId(@Param("policyId") Integer policyId);

    void deleteByPolicy_PolicyId(Integer policyId);
}

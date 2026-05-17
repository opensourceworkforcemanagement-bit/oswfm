package org.oswfm.accesscontrolservice.repository;

import java.util.List;
import java.util.Optional;

import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer > {

    Optional<Policy> findByPolicyName(String policyName);

    List<Policy> findByIsActive(Boolean isActive);

    List<Policy> findByPolicyType_PolicyTypeId(Integer policyTypeId);

    @Query("SELECT p FROM Policy p WHERE p.isActive = true ORDER BY p.priority DESC")
    List<Policy> findActivePoliciesOrderedByPriority();

    boolean existsByPolicyName(String policyName);

    /**
     * Returns active policies that are potentially relevant for a user.
     * Includes policies with no subject-category rules (apply to everyone)
     * and policies whose subject rules reference an attribute the user holds.
     *
     * @param userAttributeIds distinct attribute_definition IDs for the user's active attributes;
     *                         pass List.of(-1) if the user has no attributes
     */
    @Query("SELECT DISTINCT p FROM Policy p " +
           "WHERE p.isActive = true " +
           "AND (" +
           "  NOT EXISTS (" +
           "    SELECT 1 FROM PolicyRule pr2 WHERE pr2.policy = p " +
           "    AND LOWER(pr2.attribute.attributeCategory.categoryName) = 'subject'" +
           "  ) " +
           "  OR EXISTS (" +
           "    SELECT 1 FROM PolicyRule pr3 WHERE pr3.policy = p " +
           "    AND LOWER(pr3.attribute.attributeCategory.categoryName) = 'subject' " +
           "    AND pr3.attribute.attributeId IN :userAttributeIds" +
           "  )" +
           ") " +
           "ORDER BY p.priority DESC")
    List<Policy> findRelevantPoliciesForUser(@Param("userAttributeIds") List<Integer> userAttributeIds);
}

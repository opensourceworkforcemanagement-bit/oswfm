package org.oswfm.accesscontrolservice.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.oswfm.accesscontrolservice.dto.AccessDecisionRequest;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse.Decision;
import org.oswfm.accesscontrolservice.model.entity.AccessRequest;
import org.oswfm.accesscontrolservice.model.entity.GroupSubjectAttribute;
import org.oswfm.accesscontrolservice.model.entity.Operation;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyOperationTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyResourceTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyRule;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.model.entity.ResourceAttribute;
import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;
import org.oswfm.accesscontrolservice.model.entity.UserGroupMembership;
import org.oswfm.accesscontrolservice.model.entity.UserSubjectAttribute;

import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.AccessRequestRepository;
import org.oswfm.accesscontrolservice.repository.GroupSubjectAttributeRepository;
import org.oswfm.accesscontrolservice.repository.OperationRepository;
import org.oswfm.accesscontrolservice.repository.PolicyOperationTargetRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRepository;
import org.oswfm.accesscontrolservice.repository.PolicyResourceTargetRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRuleRepository;
import org.oswfm.accesscontrolservice.repository.ResourceAttributeRepository;
import org.oswfm.accesscontrolservice.repository.ResourceRepository;
import org.oswfm.accesscontrolservice.repository.SubjectAttributeRepository;
import org.oswfm.accesscontrolservice.repository.UserGroupMembershipRepository;
import org.oswfm.accesscontrolservice.repository.UserSubjectAttributeRepository;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyEvaluationService {

    private final PolicyRepository policyRepository;
    private final PolicyRuleRepository policyRuleRepository;
    private final PolicyResourceTargetRepository policyResourceTargetRepository;
    private final PolicyOperationTargetRepository policyOperationTargetRepository;
    private final SubjectAttributeRepository subjectAttributeRepository;
    private final ResourceAttributeRepository resourceAttributeRepository;
    private final ACUserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final OperationRepository operationRepository;
    private final AccessRequestRepository accessRequestRepository;
    private final UserSubjectAttributeRepository userSubjectAttributeRepository;
    private final GroupSubjectAttributeRepository groupSubjectAttributeRepository;
    private final UserGroupMembershipRepository userGroupMembershipRepository;

    /**
     * Main method to evaluate access decision
     */
    @Transactional
    public AccessDecisionResponse evaluateAccess(AccessDecisionRequest request) {
        log.info("Evaluating access for user: {}, resource: {}, operation: {}",
                request.getUserId(), request.getResourceId(), request.getOperationId());

        // Validate entities exist
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        Operation operation = operationRepository.findById(request.getOperationId())
                .orElseThrow(() -> new RuntimeException("Operation not found"));

        // Get all active policies ordered by priority
        List<Policy> activePolicies = policyRepository.findActivePoliciesOrderedByPriority();

        log.debug("Found {} active policies to evaluate", activePolicies.size());

        AccessDecisionResponse response = new AccessDecisionResponse(
                Decision.NOT_APPLICABLE,
                "No applicable policy found"
        );

        // Evaluate each policy in priority order
        for (Policy policy : activePolicies) {
            log.debug("Evaluating policy: {} (type: {}, priority: {})",
                    policy.getPolicyName(), policy.getPolicyType().getTypeName(), policy.getPriority());

            PolicyEvaluationResult result = evaluatePolicy(policy, user, resource, operation, request);

            if (result.isApplicable()) {
                response.addEvaluationDetail(
                    String.format("Policy '%s' is applicable", policy.getPolicyName())
                );

                if (result.isMatch()) {
                    // Policy matches - return the decision
                    response.setDecision(
                        "permit".equalsIgnoreCase(policy.getPolicyType().getTypeName()) ?
                            Decision.PERMIT : Decision.DENY
                    );
                    response.setReason(result.getReason());
                    response.setAppliedPolicyId(policy.getPolicyId());
                    response.setAppliedPolicyName(policy.getPolicyName());

                    log.info("Access decision: {} by policy: {}",
                            response.getDecision(), policy.getPolicyName());

                    // Log the access request
                    logAccessRequest(request, user, resource, operation, response, policy);

                    return response;
                } else {
                    response.addEvaluationDetail(
                        String.format("Policy '%s' applicable but conditions not met: %s",
                                policy.getPolicyName(), result.getReason())
                    );
                }
            }
        }

        // No policy matched - default to DENY
        response.setDecision(Decision.DENY);
        response.setReason("No policy explicitly permits this access");

        log.info("Access decision: DENY (no matching policy)");

        // Log the access request
        logAccessRequest(request, user, resource, operation, response, null);

        return response;
    }

    /**
     * Evaluate a single policy
     */
    private PolicyEvaluationResult evaluatePolicy(Policy policy, UserEntity user, Resource resource,
                                                   Operation operation, AccessDecisionRequest request) {

        // Check if policy targets this operation
        List<PolicyOperationTarget> operationTargets =
                policyOperationTargetRepository.findByPolicy_PolicyId(policy.getPolicyId());

        if (!operationTargets.isEmpty()) {
            boolean operationMatches = operationTargets.stream()
                    .anyMatch(target -> target.getOperation().getOperationId().equals(operation.getOperationId()));

            if (!operationMatches) {
                return PolicyEvaluationResult.notApplicable("Operation not targeted by policy");
            }
        }

        // Check if policy targets this resource
        List<PolicyResourceTarget> resourceTargets =
                policyResourceTargetRepository.findByPolicy_PolicyId(policy.getPolicyId());

        if (!resourceTargets.isEmpty()) {
            boolean resourceMatches = resourceTargets.stream()
                    .anyMatch(target -> {
                        if (target.getResource() != null) {
                            return target.getResource().getResourceId().equals(resource.getResourceId());
                        } else if (target.getResourceType() != null) {
                            return target.getResourceType().getResourceTypeId()
                                    .equals(resource.getResourceType().getResourceTypeId());
                        }
                        return false;
                    });

            if (!resourceMatches) {
                return PolicyEvaluationResult.notApplicable("Resource not targeted by policy");
            }
        }

        // Get policy rules
        List<PolicyRule> rules = policyRuleRepository.findRulesByPolicyIdOrdered(policy.getPolicyId());

        if (rules.isEmpty()) {
            // No rules means policy applies to everyone/everything
            return PolicyEvaluationResult.match("Policy has no conditions - applies to all");
        }

        // Get current subject attributes (multi-valued: direct + group)
        Map<String, List<String>> subjectAttributes = getCurrentSubjectAttributes(user.getUserId());

        // Get current resource attributes (multi-valued)
        Map<String, List<String>> resourceAttributes = getCurrentResourceAttributes(resource.getResourceId());

        // Evaluate all rules
        return evaluateRules(rules, subjectAttributes, resourceAttributes, request.getEnvironmentAttributes());
    }

    /**
     * Evaluate policy rules with AND/OR logic
     */
    private PolicyEvaluationResult evaluateRules(List<PolicyRule> rules,
                                                  Map<String, List<String>> subjectAttributes,
                                                  Map<String, List<String>> resourceAttributes,
                                                  Map<String, String> environmentAttributes) {

        if (rules.isEmpty()) {
            return PolicyEvaluationResult.match("No rules to evaluate");
        }

        boolean overallResult = true;
        String currentLogicalOp = "AND";
        StringBuilder reasonBuilder = new StringBuilder();

        for (PolicyRule rule : rules) {
            String attributeName = rule.getAttribute().getAttributeName();
            String attributeCategory = rule.getAttribute().getAttributeCategory().getCategoryName();

            // Get the actual attribute values based on category (multi-valued)
            List<String> actualValues = getAttributeValues(
                    attributeName,
                    attributeCategory,
                    subjectAttributes,
                    resourceAttributes,
                    environmentAttributes
            );

            if (actualValues == null || actualValues.isEmpty()) {
                log.debug("Attribute '{}' not found for user/resource", attributeName);

                // If AND logic and attribute missing, rule fails
                if ("AND".equalsIgnoreCase(currentLogicalOp)) {
                    return PolicyEvaluationResult.noMatch(
                        String.format("Required attribute '%s' not found", attributeName)
                    );
                }
                // For OR logic, continue to next rule
                continue;
            }

            // Evaluate this rule against all values
            boolean ruleResult = evaluateMultiValueRule(
                    rule.getOperator(),
                    actualValues,
                    rule.getComparisonValue()
            );

            log.debug("Rule evaluation: {} {} {} = {}",
                    actualValues, rule.getOperator(), rule.getComparisonValue(), ruleResult);

            // Apply logical operator
            if ("OR".equalsIgnoreCase(currentLogicalOp)) {
                overallResult = overallResult || ruleResult;
            } else { // AND
                overallResult = overallResult && ruleResult;
            }

            // Update current logical operator for next iteration
            currentLogicalOp = rule.getLogicalOperator();

            // Build reason
            if (!ruleResult) {
                reasonBuilder.append(String.format(
                    "Condition failed: %s %s %s (actual: %s); ",
                    attributeName, rule.getOperator(), rule.getComparisonValue(), actualValues
                ));
            }
        }

        if (overallResult) {
            return PolicyEvaluationResult.match("All conditions satisfied");
        } else {
            return PolicyEvaluationResult.noMatch(
                reasonBuilder.length() > 0 ?
                    reasonBuilder.toString() :
                    "One or more conditions not satisfied"
            );
        }
    }

    /**
     * Evaluate a single rule against multiple attribute values.
     * For most operators, returns true if ANY value satisfies the condition.
     * For negation operators (not_equals, not_in, not_contains), returns true
     * only if ALL values satisfy the condition.
     */
    private boolean evaluateMultiValueRule(String operator, List<String> actualValues, String comparisonValue) {
        if (actualValues == null || actualValues.isEmpty()) {
            return false;
        }

        switch (operator.toLowerCase()) {
            case "equals":
                return actualValues.stream()
                        .anyMatch(v -> v.equalsIgnoreCase(comparisonValue));

            case "not_equals":
                return actualValues.stream()
                        .noneMatch(v -> v.equalsIgnoreCase(comparisonValue));

            case "contains":
                return actualValues.stream()
                        .anyMatch(v -> v.toLowerCase().contains(comparisonValue.toLowerCase()));

            case "not_contains":
                return actualValues.stream()
                        .noneMatch(v -> v.toLowerCase().contains(comparisonValue.toLowerCase()));

            case "in": {
                String[] compareValues = comparisonValue.split(",");
                return actualValues.stream()
                        .anyMatch(actual -> Arrays.stream(compareValues)
                                .map(String::trim)
                                .anyMatch(cv -> cv.equalsIgnoreCase(actual)));
            }

            case "not_in": {
                String[] notInValues = comparisonValue.split(",");
                return actualValues.stream()
                        .allMatch(actual -> Arrays.stream(notInValues)
                                .map(String::trim)
                                .noneMatch(cv -> cv.equalsIgnoreCase(actual)));
            }

            case "greater_than":
                return actualValues.stream().anyMatch(v -> {
                    try {
                        return Double.parseDouble(v) > Double.parseDouble(comparisonValue);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                });

            case "less_than":
                return actualValues.stream().anyMatch(v -> {
                    try {
                        return Double.parseDouble(v) < Double.parseDouble(comparisonValue);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                });

            case "greater_than_or_equal":
                return actualValues.stream().anyMatch(v -> {
                    try {
                        return Double.parseDouble(v) >= Double.parseDouble(comparisonValue);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                });

            case "less_than_or_equal":
                return actualValues.stream().anyMatch(v -> {
                    try {
                        return Double.parseDouble(v) <= Double.parseDouble(comparisonValue);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                });

            case "starts_with":
                return actualValues.stream()
                        .anyMatch(v -> v.toLowerCase().startsWith(comparisonValue.toLowerCase()));

            case "ends_with":
                return actualValues.stream()
                        .anyMatch(v -> v.toLowerCase().endsWith(comparisonValue.toLowerCase()));

            default:
                log.warn("Unknown operator: {}", operator);
                return false;
        }
    }

    /**
     * Get attribute values based on category (multi-valued).
     * Environment attributes come from the request as single-valued, so they are wrapped in a list.
     */
    private List<String> getAttributeValues(String attributeName, String category,
                                            Map<String, List<String>> subjectAttrs,
                                            Map<String, List<String>> resourceAttrs,
                                            Map<String, String> envAttrs) {
        switch (category.toLowerCase()) {
            case "subject":
                return subjectAttrs.getOrDefault(attributeName, Collections.emptyList());
            case "resource":
                return resourceAttrs.getOrDefault(attributeName, Collections.emptyList());
            case "environment":
            case "operation":
                String envValue = envAttrs != null ? envAttrs.get(attributeName) : null;
                return envValue != null ? List.of(envValue) : Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }

    /**
     * Get current active subject attributes for a user.
     * Merges attributes from direct assignments and group memberships.
     * Returns multi-valued map: one attribute name can have multiple values.
     */
    private Map<String, List<String>> getCurrentSubjectAttributes(Integer userId) {
        OffsetDateTime now = OffsetDateTime.now();
        Map<String, List<String>> result = new HashMap<>();

        // 1. Collect subject_attr_ids from direct user assignments
        List<UserSubjectAttribute> directAssignments =
                userSubjectAttributeRepository.findByUser_UserId(userId);
        List<Integer> directAttrIds = directAssignments.stream()
                .map(usa -> usa.getSubjectAttribute().getSubjectAttrId())
                .collect(Collectors.toList());

        // 2. Collect subject_attr_ids from group memberships
        List<UserGroupMembership> memberships =
                userGroupMembershipRepository.findByUser_UserId(userId);
        List<Integer> groupIds = memberships.stream()
                .map(m -> m.getGroup().getGroupId())
                .collect(Collectors.toList());

        List<Integer> groupAttrIds = Collections.emptyList();
        if (!groupIds.isEmpty()) {
            List<GroupSubjectAttribute> groupAssignments =
                    groupSubjectAttributeRepository.findByGroup_GroupIdIn(groupIds);
            groupAttrIds = groupAssignments.stream()
                    .map(gsa -> gsa.getSubjectAttribute().getSubjectAttrId())
                    .collect(Collectors.toList());
        }

        // 3. Combine all ids and fetch active attributes
        List<Integer> allAttrIds = new ArrayList<>();
        allAttrIds.addAll(directAttrIds);
        allAttrIds.addAll(groupAttrIds);

        if (allAttrIds.isEmpty()) {
            return result;
        }

        List<SubjectAttribute> activeAttributes =
                subjectAttributeRepository.findActiveByIds(allAttrIds, now);

        // 4. Build multi-valued map
        for (SubjectAttribute attr : activeAttributes) {
            result.computeIfAbsent(attr.getAttribute().getAttributeName(), k -> new ArrayList<>())
                    .add(attr.getAttributeValue());
        }

        return result;
    }

    /**
     * Get current active resource attributes (multi-valued)
     */
    private Map<String, List<String>> getCurrentResourceAttributes(Integer resourceId) {
        List<ResourceAttribute> attributes =
                resourceAttributeRepository.findActiveAttributesByResourceId(resourceId, OffsetDateTime.now());

        Map<String, List<String>> result = new HashMap<>();
        for (ResourceAttribute attr : attributes) {
            result.computeIfAbsent(attr.getAttribute().getAttributeName(), k -> new ArrayList<>())
                    .add(attr.getAttributeValue());
        }
        return result;
    }

    /**
     * Log access request to database
     */
    private void logAccessRequest(AccessDecisionRequest request, UserEntity user, Resource resource,
                                  Operation operation, AccessDecisionResponse response, Policy appliedPolicy) {
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUser(user);
        accessRequest.setResource(resource);
        accessRequest.setOperation(operation);
        accessRequest.setDecision(response.getDecision().name());
        accessRequest.setDecisionReason(response.getReason());
        accessRequest.setAppliedPolicy(appliedPolicy);
        accessRequest.setSourceIp(request.getSourceIp());
        accessRequest.setUserAgent(request.getUserAgent());
        accessRequest.setSessionId(request.getSessionId());

        accessRequestRepository.save(accessRequest);
    }

    /**
     * Helper class to hold policy evaluation results
     */
    private static class PolicyEvaluationResult {
        private final boolean applicable;
        private final boolean match;
        private final String reason;

        private PolicyEvaluationResult(boolean applicable, boolean match, String reason) {
            this.applicable = applicable;
            this.match = match;
            this.reason = reason;
        }

        public static PolicyEvaluationResult notApplicable(String reason) {
            return new PolicyEvaluationResult(false, false, reason);
        }

        public static PolicyEvaluationResult match(String reason) {
            return new PolicyEvaluationResult(true, true, reason);
        }

        public static PolicyEvaluationResult noMatch(String reason) {
            return new PolicyEvaluationResult(true, false, reason);
        }

        public boolean isApplicable() {
            return applicable;
        }

        public boolean isMatch() {
            return match;
        }

        public String getReason() {
            return reason;
        }
    }
}

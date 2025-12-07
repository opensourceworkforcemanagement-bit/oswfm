package org.oswfm.accesscontrolservice.service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.oswfm.accesscontrolservice.dto.AccessDecisionRequest;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse;
import org.oswfm.accesscontrolservice.dto.AccessDecisionResponse.Decision;
import org.oswfm.accesscontrolservice.model.entity.AccessRequest;
import org.oswfm.accesscontrolservice.model.entity.Action;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyActionTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyResourceTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyRule;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.model.entity.ResourceAttribute;
import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;

import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.AccessRequestRepository;
import org.oswfm.accesscontrolservice.repository.ActionRepository;
import org.oswfm.accesscontrolservice.repository.PolicyActionTargetRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRepository;
import org.oswfm.accesscontrolservice.repository.PolicyResourceTargetRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRuleRepository;
import org.oswfm.accesscontrolservice.repository.ResourceAttributeRepository;
import org.oswfm.accesscontrolservice.repository.ResourceRepository;
import org.oswfm.accesscontrolservice.repository.SubjectAttributeRepository;
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
    private final PolicyActionTargetRepository policyActionTargetRepository;
    private final SubjectAttributeRepository subjectAttributeRepository;
    private final ResourceAttributeRepository resourceAttributeRepository;
    private final ACUserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final ActionRepository actionRepository;
    private final AccessRequestRepository accessRequestRepository;
    
    /**
     * Main method to evaluate access decision
     */
    @Transactional
    public AccessDecisionResponse evaluateAccess(AccessDecisionRequest request) {
        log.info("Evaluating access for user: {}, resource: {}, action: {}", 
                request.getUserId(), request.getResourceId(), request.getActionId());
        
        // Validate entities exist
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        Action action = actionRepository.findById(request.getActionId())
                .orElseThrow(() -> new RuntimeException("Action not found"));
        
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
                    policy.getPolicyName(), policy.getPolicyType(), policy.getPriority());
            
            PolicyEvaluationResult result = evaluatePolicy(policy, user, resource, action, request);
            
            if (result.isApplicable()) {
                response.addEvaluationDetail(
                    String.format("Policy '%s' is applicable", policy.getPolicyName())
                );
                
                if (result.isMatch()) {
                    // Policy matches - return the decision
                    response.setDecision(
                        "permit".equalsIgnoreCase(policy.getPolicyType()) ? 
                            Decision.PERMIT : Decision.DENY
                    );
                    response.setReason(result.getReason());
                    response.setAppliedPolicyId(policy.getPolicyId());
                    response.setAppliedPolicyName(policy.getPolicyName());
                    
                    log.info("Access decision: {} by policy: {}", 
                            response.getDecision(), policy.getPolicyName());
                    
                    // Log the access request
                    logAccessRequest(request, user, resource, action, response, policy);
                    
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
        logAccessRequest(request, user, resource, action, response, null);
        
        return response;
    }
    
    /**
     * Evaluate a single policy
     */
    private PolicyEvaluationResult evaluatePolicy(Policy policy, UserEntity user, Resource resource, 
                                                   Action action, AccessDecisionRequest request) {
        
        // Check if policy targets this action
        List<PolicyActionTarget> actionTargets = 
                policyActionTargetRepository.findByPolicy_PolicyId(policy.getPolicyId());
        
        if (!actionTargets.isEmpty()) {
            boolean actionMatches = actionTargets.stream()
                    .anyMatch(target -> target.getAction().getActionId().equals(action.getActionId()));
            
            if (!actionMatches) {
                return PolicyEvaluationResult.notApplicable("Action not targeted by policy");
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
                            return target.getResourceType().equalsIgnoreCase(resource.getResourceType());
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
        
        // Get current subject attributes
        Map<String, String> subjectAttributes = getCurrentSubjectAttributes(user.getUserId());
        
        // Get current resource attributes
        Map<String, String> resourceAttributes = getCurrentResourceAttributes(resource.getResourceId());
        
        // Evaluate all rules
        return evaluateRules(rules, subjectAttributes, resourceAttributes, request.getEnvironmentAttributes());
    }
    
    /**
     * Evaluate policy rules with AND/OR logic
     */
    private PolicyEvaluationResult evaluateRules(List<PolicyRule> rules, 
                                                  Map<String, String> subjectAttributes,
                                                  Map<String, String> resourceAttributes,
                                                  Map<String, String> environmentAttributes) {
        
        if (rules.isEmpty()) {
            return PolicyEvaluationResult.match("No rules to evaluate");
        }
        
        boolean overallResult = true;
        String currentLogicalOp = "AND";
        StringBuilder reasonBuilder = new StringBuilder();
        
        for (PolicyRule rule : rules) {
            String attributeName = rule.getAttribute().getAttributeName();
            String attributeCategory = rule.getAttribute().getAttributeCategory();
            
            // Get the actual attribute value based on category
            String actualValue = getAttributeValue(
                    attributeName, 
                    attributeCategory, 
                    subjectAttributes, 
                    resourceAttributes, 
                    environmentAttributes
            );
            
            if (actualValue == null) {
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
            
            // Evaluate this rule
            boolean ruleResult = evaluateSingleRule(
                    rule.getOperator(), 
                    actualValue, 
                    rule.getComparisonValue()
            );
            
            log.debug("Rule evaluation: {} {} {} = {}", 
                    actualValue, rule.getOperator(), rule.getComparisonValue(), ruleResult);
            
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
                    attributeName, rule.getOperator(), rule.getComparisonValue(), actualValue
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
     * Evaluate a single rule based on operator
     */
    private boolean evaluateSingleRule(String operator, String actualValue, String comparisonValue) {
        if (actualValue == null) {
            return false;
        }
        
        switch (operator.toLowerCase()) {
            case "equals":
                return actualValue.equalsIgnoreCase(comparisonValue);
                
            case "not_equals":
                return !actualValue.equalsIgnoreCase(comparisonValue);
                
            case "contains":
                return actualValue.toLowerCase().contains(comparisonValue.toLowerCase());
                
            case "not_contains":
                return !actualValue.toLowerCase().contains(comparisonValue.toLowerCase());
                
            case "in":
                // comparisonValue should be comma-separated list
                String[] values = comparisonValue.split(",");
                return Arrays.stream(values)
                        .map(String::trim)
                        .anyMatch(v -> v.equalsIgnoreCase(actualValue));
                
            case "not_in":
                String[] notInValues = comparisonValue.split(",");
                return Arrays.stream(notInValues)
                        .map(String::trim)
                        .noneMatch(v -> v.equalsIgnoreCase(actualValue));
                
            case "greater_than":
                try {
                    return Double.parseDouble(actualValue) > Double.parseDouble(comparisonValue);
                } catch (NumberFormatException e) {
                    log.warn("Cannot compare non-numeric values with greater_than");
                    return false;
                }
                
            case "less_than":
                try {
                    return Double.parseDouble(actualValue) < Double.parseDouble(comparisonValue);
                } catch (NumberFormatException e) {
                    log.warn("Cannot compare non-numeric values with less_than");
                    return false;
                }
                
            case "greater_than_or_equal":
                try {
                    return Double.parseDouble(actualValue) >= Double.parseDouble(comparisonValue);
                } catch (NumberFormatException e) {
                    return false;
                }
                
            case "less_than_or_equal":
                try {
                    return Double.parseDouble(actualValue) <= Double.parseDouble(comparisonValue);
                } catch (NumberFormatException e) {
                    return false;
                }
                
            case "starts_with":
                return actualValue.toLowerCase().startsWith(comparisonValue.toLowerCase());
                
            case "ends_with":
                return actualValue.toLowerCase().endsWith(comparisonValue.toLowerCase());
                
            default:
                log.warn("Unknown operator: {}", operator);
                return false;
        }
    }
    
    /**
     * Get attribute value based on category
     */
    private String getAttributeValue(String attributeName, String category,
                                     Map<String, String> subjectAttrs,
                                     Map<String, String> resourceAttrs,
                                     Map<String, String> envAttrs) {
        switch (category.toLowerCase()) {
            case "subject":
                return subjectAttrs.get(attributeName);
            case "resource":
                return resourceAttrs.get(attributeName);
            case "environment":
            case "action":
                return envAttrs.get(attributeName);
            default:
                return null;
        }
    }
    
    /**
     * Get current active subject attributes for a user
     */
    private Map<String, String> getCurrentSubjectAttributes(Integer userId) {
        List<SubjectAttribute> attributes = 
                subjectAttributeRepository.findActiveAttributesByUserId(userId, OffsetDateTime.now());
        
        return attributes.stream()
                .collect(Collectors.toMap(
                    attr -> attr.getAttribute().getAttributeName(),
                    SubjectAttribute::getAttributeValue,
                    (v1, v2) -> v1  // If duplicate keys, keep first
                ));
    }
    
    /**
     * Get current active resource attributes
     */
    private Map<String, String> getCurrentResourceAttributes( Integer resourceId) {
        List<ResourceAttribute> attributes = 
                resourceAttributeRepository.findActiveAttributesByResourceId(resourceId, OffsetDateTime.now());
        
        return attributes.stream()
                .collect(Collectors.toMap(
                    attr -> attr.getAttribute().getAttributeName(),
                    ResourceAttribute::getAttributeValue,
                    (v1, v2) -> v1
                ));
    }
    
    /**
     * Log access request to database
     */
    private void logAccessRequest(AccessDecisionRequest request, UserEntity user, Resource resource, 
                                  Action action, AccessDecisionResponse response, Policy appliedPolicy) {
        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUser(user);
        accessRequest.setResource(resource);
        accessRequest.setAction(action);
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

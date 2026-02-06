package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.PolicyRuleDTO;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyRule;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyRuleService {
    
    private final PolicyRuleRepository policyRuleRepository;
    private final PolicyRepository policyRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    
    @Transactional(readOnly = true)
    public List<PolicyRuleDTO> getAllPolicyRules() {
        return policyRuleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PolicyRuleDTO getPolicyRuleById( Integer id) {
        PolicyRule policyRule = policyRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyRule", "id", id));
        return convertToDTO(policyRule);
    }
    
    @Transactional(readOnly = true)
    public List<PolicyRuleDTO> getRulesByPolicyId( Integer policyId) {
        return policyRuleRepository.findRulesByPolicyIdOrdered(policyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PolicyRuleDTO createPolicyRule(PolicyRuleDTO policyRuleDTO) {
        Policy policy = policyRepository.findById(policyRuleDTO.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyRuleDTO.getPolicyId()));
        
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findById(policyRuleDTO.getAttributeId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", policyRuleDTO.getAttributeId()));
        
        PolicyRule policyRule = new PolicyRule();
        policyRule.setPolicy(policy);
        policyRule.setAttribute(attributeDefinition);
        policyRule.setRuleName(policyRuleDTO.getRuleName());
        policyRule.setOperator(policyRuleDTO.getOperator());
        policyRule.setComparisonValue(policyRuleDTO.getComparisonValue());
        policyRule.setLogicalOperator(policyRuleDTO.getLogicalOperator() != null ? 
                policyRuleDTO.getLogicalOperator() : "AND");
        policyRule.setRuleOrder(policyRuleDTO.getRuleOrder() != null ? 
                policyRuleDTO.getRuleOrder() : 0);
        
        PolicyRule savedPolicyRule = policyRuleRepository.save(policyRule);
        return convertToDTO(savedPolicyRule);
    }
    
    @Transactional
    public PolicyRuleDTO updatePolicyRule( Integer id, PolicyRuleDTO policyRuleDTO) {
        PolicyRule existingPolicyRule = policyRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyRule", "id", id));
        
        existingPolicyRule.setRuleName(policyRuleDTO.getRuleName());
        existingPolicyRule.setOperator(policyRuleDTO.getOperator());
        existingPolicyRule.setComparisonValue(policyRuleDTO.getComparisonValue());
        existingPolicyRule.setLogicalOperator(policyRuleDTO.getLogicalOperator());
        existingPolicyRule.setRuleOrder(policyRuleDTO.getRuleOrder());
        
        PolicyRule updatedPolicyRule = policyRuleRepository.save(existingPolicyRule);
        return convertToDTO(updatedPolicyRule);
    }
    
    @Transactional
    public void deletePolicyRule( Integer id) {
        if (!policyRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("PolicyRule", "id", id);
        }
        policyRuleRepository.deleteById(id);
    }
    
    @Transactional
    public void deleteRulesByPolicyId( Integer policyId) {
        policyRuleRepository.deleteByPolicy_PolicyId(policyId);
    }
    
    private PolicyRuleDTO convertToDTO(PolicyRule policyRule) {
        PolicyRuleDTO dto = new PolicyRuleDTO();
        dto.setRuleId(policyRule.getRuleId());
        dto.setPolicyId(policyRule.getPolicy().getPolicyId());
        dto.setRuleName(policyRule.getRuleName());
        dto.setAttributeId(policyRule.getAttribute().getAttributeId());
        dto.setAttributeName(policyRule.getAttribute().getAttributeName());
        dto.setOperator(policyRule.getOperator());
        dto.setComparisonValue(policyRule.getComparisonValue());
        dto.setLogicalOperator(policyRule.getLogicalOperator());
        dto.setRuleOrder(policyRule.getRuleOrder());
        dto.setCreatedAt(policyRule.getCreatedAt());
        return dto;
    }
}

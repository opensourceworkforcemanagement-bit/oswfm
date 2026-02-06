package org.oswfm.accesscontrolservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.PolicyDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyType;
import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRepository;
import org.oswfm.accesscontrolservice.repository.PolicyTypeRepository;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyTypeRepository policyTypeRepository;
    private final ACUserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PolicyDTO getPolicyById( Integer id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", id));
        return convertToDTO(policy);
    }
    
    @Transactional(readOnly = true)
    public List<PolicyDTO> getActivePolicies() {
        return policyRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PolicyDTO> getPoliciesByType(Integer policyTypeId) {
        return policyRepository.findByPolicyType_PolicyTypeId(policyTypeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PolicyDTO createPolicy(PolicyDTO policyDTO) {
        if (policyRepository.existsByPolicyName(policyDTO.getPolicyName())) {
            throw new DuplicateResourceException("Policy", "policyName", policyDTO.getPolicyName());
        }
        
        Policy policy = convertToEntity(policyDTO);
        
        if (policyDTO.getCreatedById() != null) {
            UserEntity creator = userRepository.findById(policyDTO.getCreatedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", policyDTO.getCreatedById()));
            policy.setCreatedBy(creator);
        }
        
        Policy savedPolicy = policyRepository.save(policy);
        return convertToDTO(savedPolicy);
    }
    
    @Transactional
    public PolicyDTO updatePolicy( Integer id, PolicyDTO policyDTO) {
        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", id));
        
        if (!existingPolicy.getPolicyName().equals(policyDTO.getPolicyName()) && 
            policyRepository.existsByPolicyName(policyDTO.getPolicyName())) {
            throw new DuplicateResourceException("Policy", "policyName", policyDTO.getPolicyName());
        }
        
        PolicyType policyType = policyTypeRepository.findById(policyDTO.getPolicyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("PolicyType", "id", policyDTO.getPolicyTypeId()));

        existingPolicy.setPolicyName(policyDTO.getPolicyName());
        existingPolicy.setDescription(policyDTO.getDescription());
        existingPolicy.setPolicyType(policyType);
        existingPolicy.setPriority(policyDTO.getPriority());
        existingPolicy.setIsActive(policyDTO.getIsActive());
        
        Policy updatedPolicy = policyRepository.save(existingPolicy);
        return convertToDTO(updatedPolicy);
    }
    
    @Transactional
    public void deletePolicy( Integer id) {
        if (!policyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Policy", "id", id);
        }
        policyRepository.deleteById(id);
    }
    
    private PolicyDTO convertToDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyId(policy.getPolicyId());
        dto.setPolicyName(policy.getPolicyName());
        dto.setDescription(policy.getDescription());
        dto.setPolicyTypeId(policy.getPolicyType().getPolicyTypeId());
        dto.setPolicyTypeName(policy.getPolicyType().getTypeName());
        dto.setPriority(policy.getPriority());
        dto.setIsActive(policy.getIsActive());
        dto.setCreatedAt(policy.getCreatedAt());
        dto.setUpdatedAt(policy.getUpdatedAt());

        if (policy.getCreatedBy() != null) {
            dto.setCreatedById(policy.getCreatedBy().getUserId());
            dto.setCreatedByUsername(policy.getCreatedBy().getUserName());
        }

        return dto;
    }

    private Policy convertToEntity(PolicyDTO dto) {
        Policy policy = new Policy();

        PolicyType policyType = policyTypeRepository.findById(dto.getPolicyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("PolicyType", "id", dto.getPolicyTypeId()));

        policy.setPolicyName(dto.getPolicyName());
        policy.setDescription(dto.getDescription());
        policy.setPolicyType(policyType);
        policy.setPriority(dto.getPriority() != null ? dto.getPriority() : 0);
        policy.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return policy;
    }
}

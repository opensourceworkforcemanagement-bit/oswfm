package org.oswfm.accesscontrolservice.service;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.PolicyObligationDTO;
import org.oswfm.accesscontrolservice.model.entity.ObligationType;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyObligation;
import org.oswfm.accesscontrolservice.repository.ObligationTypeRepository;
import org.oswfm.accesscontrolservice.repository.PolicyObligationRepository;
import org.oswfm.accesscontrolservice.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyObligationService {

    private final PolicyObligationRepository policyObligationRepository;
    private final PolicyRepository policyRepository;
    private final ObligationTypeRepository obligationTypeRepository;

    @Transactional(readOnly = true)
    public List<PolicyObligationDTO> getAllPolicyObligations() {
        return policyObligationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PolicyObligationDTO getPolicyObligationById(Integer id) {
        PolicyObligation obligation = policyObligationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyObligation", "id", id));
        return convertToDTO(obligation);
    }

    @Transactional(readOnly = true)
    public List<PolicyObligationDTO> getObligationsByPolicyId(Integer policyId) {
        return policyObligationRepository.findObligationsByPolicyId(policyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PolicyObligationDTO> getObligationsByType(Integer obligationTypeId) {
        return policyObligationRepository.findByObligationType_ObligationTypeId(obligationTypeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PolicyObligationDTO createPolicyObligation(PolicyObligationDTO obligationDTO) {
        Policy policy = policyRepository.findById(obligationDTO.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", obligationDTO.getPolicyId()));

        ObligationType obligationType = obligationTypeRepository.findById(obligationDTO.getObligationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ObligationType", "id", obligationDTO.getObligationTypeId()));

        PolicyObligation obligation = new PolicyObligation();
        obligation.setPolicy(policy);
        obligation.setObligationType(obligationType);
        obligation.setObligationParams(obligationDTO.getObligationParams());
        obligation.setIsMandatory(obligationDTO.getIsMandatory() != null ? obligationDTO.getIsMandatory() : true);

        PolicyObligation savedObligation = policyObligationRepository.save(obligation);
        return convertToDTO(savedObligation);
    }

    @Transactional
    public PolicyObligationDTO updatePolicyObligation(Integer id, PolicyObligationDTO obligationDTO) {
        PolicyObligation existingObligation = policyObligationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyObligation", "id", id));

        ObligationType obligationType = obligationTypeRepository.findById(obligationDTO.getObligationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ObligationType", "id", obligationDTO.getObligationTypeId()));

        existingObligation.setObligationType(obligationType);
        existingObligation.setObligationParams(obligationDTO.getObligationParams());
        existingObligation.setIsMandatory(obligationDTO.getIsMandatory());

        PolicyObligation updatedObligation = policyObligationRepository.save(existingObligation);
        return convertToDTO(updatedObligation);
    }

    @Transactional
    public void deletePolicyObligation(Integer id) {
        if (!policyObligationRepository.existsById(id)) {
            throw new ResourceNotFoundException("PolicyObligation", "id", id);
        }
        policyObligationRepository.deleteById(id);
    }

    @Transactional
    public void deleteObligationsByPolicyId(Integer policyId) {
        policyObligationRepository.deleteByPolicy_PolicyId(policyId);
    }

    private PolicyObligationDTO convertToDTO(PolicyObligation obligation) {
        PolicyObligationDTO dto = new PolicyObligationDTO();
        dto.setObligationId(obligation.getObligationId());
        dto.setPolicyId(obligation.getPolicy().getPolicyId());
        dto.setObligationTypeId(obligation.getObligationType().getObligationTypeId());
        dto.setObligationTypeName(obligation.getObligationType().getTypeName());
        dto.setObligationParams(obligation.getObligationParams());
        dto.setIsMandatory(obligation.getIsMandatory());
        dto.setCreatedAt(obligation.getCreatedAt());
        return dto;
    }
}

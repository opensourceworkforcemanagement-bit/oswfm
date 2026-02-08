package org.oswfm.accesscontrolservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.OperationDTO;
import org.oswfm.accesscontrolservice.dto.ResourceDTO;
import org.oswfm.accesscontrolservice.model.entity.Operation;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyOperationTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyResourceTarget;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.model.entity.ResourceType;
import org.oswfm.accesscontrolservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PolicyTargetService {

    private final PolicyRepository policyRepository;
    private final OperationRepository operationRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceTypeRepository resourceTypeRepository;
    private final PolicyOperationTargetRepository policyOperationTargetRepository;
    private final PolicyResourceTargetRepository policyResourceTargetRepository;

    @Transactional
    public void addOperationToPolicy( Integer policyId, Integer  operationId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));

        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new ResourceNotFoundException("Operation", "id", operationId));

        PolicyOperationTarget target = new PolicyOperationTarget();
        target.setPolicy(policy);
        target.setOperation(operation);

        policyOperationTargetRepository.save(target);
    }

    @Transactional
    public void removeOperationFromPolicy( Integer policyId, Integer  operationId) {
        policyOperationTargetRepository.findByPolicy_PolicyId(policyId).stream()
                .filter(target -> target.getOperation().getOperationId().equals(operationId))
                .forEach(policyOperationTargetRepository::delete);
    }

    @Transactional
    public void addResourceToPolicy( Integer policyId, Integer  resourceId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", resourceId));

        PolicyResourceTarget target = new PolicyResourceTarget();
        target.setPolicy(policy);
        target.setResource(resource);

        policyResourceTargetRepository.save(target);
    }

    @Transactional
    public void addResourceTypeToPolicy(Integer policyId, Integer resourceTypeId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));

        ResourceType resourceType = resourceTypeRepository.findById(resourceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceType", "id", resourceTypeId));

        PolicyResourceTarget target = new PolicyResourceTarget();
        target.setPolicy(policy);
        target.setResourceType(resourceType);

        policyResourceTargetRepository.save(target);
    }

    @Transactional
    public void removeResourceFromPolicy( Integer policyId, Integer  resourceId) {
        policyResourceTargetRepository.findByPolicy_PolicyId(policyId).stream()
                .filter(target -> target.getResource() != null &&
                        target.getResource().getResourceId().equals(resourceId))
                .forEach(policyResourceTargetRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<OperationDTO> getOperationsForPolicy(Integer policyId) {
        return policyOperationTargetRepository.findByPolicy_PolicyId(policyId).stream()
                .map(target -> {
                    Operation op = target.getOperation();
                    OperationDTO dto = new OperationDTO();
                    dto.setOperationId(op.getOperationId());
                    dto.setOperationName(op.getOperationName());
                    dto.setDescription(op.getDescription());
                    dto.setCreatedAt(op.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesForPolicy(Integer policyId) {
        return policyResourceTargetRepository.findByPolicy_PolicyId(policyId).stream()
                .filter(target -> target.getResource() != null)
                .map(target -> {
                    Resource res = target.getResource();
                    ResourceDTO dto = new ResourceDTO();
                    dto.setResourceId(res.getResourceId());
                    dto.setResourceName(res.getResourceName());
                    dto.setResourceTypeId(res.getResourceType() != null ? res.getResourceType().getResourceTypeId() : null);
                    dto.setResourceTypeName(res.getResourceType() != null ? res.getResourceType().getTypeName() : null);
                    dto.setResourceUri(res.getResourceUri());
                    dto.setIsActive(res.getIsActive());
                    dto.setCreatedAt(res.getCreatedAt());
                    dto.setUpdatedAt(res.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

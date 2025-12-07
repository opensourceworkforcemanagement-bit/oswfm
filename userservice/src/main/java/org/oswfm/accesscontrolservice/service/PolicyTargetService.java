package org.oswfm.accesscontrolservice.service;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.model.entity.Action;
import org.oswfm.accesscontrolservice.model.entity.Policy;
import org.oswfm.accesscontrolservice.model.entity.PolicyActionTarget;
import org.oswfm.accesscontrolservice.model.entity.PolicyResourceTarget;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyTargetService {
    
    private final PolicyRepository policyRepository;
    private final ActionRepository actionRepository;
    private final ResourceRepository resourceRepository;
    private final PolicyActionTargetRepository policyActionTargetRepository;
    private final PolicyResourceTargetRepository policyResourceTargetRepository;
    
    @Transactional
    public void addActionToPolicy( Integer policyId, Integer  actionId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));
        
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Action", "id", actionId));
        
        PolicyActionTarget target = new PolicyActionTarget();
        target.setPolicy(policy);
        target.setAction(action);
        
        policyActionTargetRepository.save(target);
    }
    
    @Transactional
    public void removeActionFromPolicy( Integer policyId, Integer  actionId) {
        policyActionTargetRepository.findByPolicy_PolicyId(policyId).stream()
                .filter(target -> target.getAction().getActionId().equals(actionId))
                .forEach(policyActionTargetRepository::delete);
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
    public void addResourceTypeToPolicy( Integer policyId, String resourceType) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy", "id", policyId));
        
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
}

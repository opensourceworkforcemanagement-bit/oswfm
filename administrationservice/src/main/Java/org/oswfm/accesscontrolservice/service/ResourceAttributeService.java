package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.ResourceAttributeDTO;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.model.entity.ResourceAttribute;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import org.oswfm.accesscontrolservice.repository.ResourceAttributeRepository;
import org.oswfm.accesscontrolservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceAttributeService {
    
    private final ResourceAttributeRepository resourceAttributeRepository;
    private final ResourceRepository resourceRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    
    @Transactional(readOnly = true)
    public List<ResourceAttributeDTO> getAllResourceAttributes() {
        return resourceAttributeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ResourceAttributeDTO getResourceAttributeById( Integer id) {
        ResourceAttribute resourceAttribute = resourceAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceAttribute", "id", id));
        return convertToDTO(resourceAttribute);
    }
    
    @Transactional(readOnly = true)
    public List<ResourceAttributeDTO> getResourceAttributesByResourceId( Integer resourceId) {
        return resourceAttributeRepository.findByResource_ResourceId(resourceId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ResourceAttributeDTO> getActiveResourceAttributesByResourceId( Integer resourceId) {
        return resourceAttributeRepository.findActiveAttributesByResourceId(resourceId, OffsetDateTime.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ResourceAttributeDTO createResourceAttribute(ResourceAttributeDTO resourceAttributeDTO) {
        Resource resource = resourceRepository.findById(resourceAttributeDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", resourceAttributeDTO.getResourceId()));
        
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findById(resourceAttributeDTO.getAttributeId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", resourceAttributeDTO.getAttributeId()));
        
        ResourceAttribute resourceAttribute = new ResourceAttribute();
        resourceAttribute.setResource(resource);
        resourceAttribute.setAttribute(attributeDefinition);
        resourceAttribute.setAttributeValue(resourceAttributeDTO.getAttributeValue());
        resourceAttribute.setValidFrom(resourceAttributeDTO.getValidFrom() != null ? 
                resourceAttributeDTO.getValidFrom() : OffsetDateTime.now());
        resourceAttribute.setValidUntil(resourceAttributeDTO.getValidUntil());
        
        ResourceAttribute savedResourceAttribute = resourceAttributeRepository.save(resourceAttribute);
        return convertToDTO(savedResourceAttribute);
    }
    
    @Transactional
    public ResourceAttributeDTO updateResourceAttribute( Integer id, ResourceAttributeDTO resourceAttributeDTO) {
        ResourceAttribute existingResourceAttribute = resourceAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceAttribute", "id", id));
        
        existingResourceAttribute.setAttributeValue(resourceAttributeDTO.getAttributeValue());
        existingResourceAttribute.setValidFrom(resourceAttributeDTO.getValidFrom());
        existingResourceAttribute.setValidUntil(resourceAttributeDTO.getValidUntil());
        
        ResourceAttribute updatedResourceAttribute = resourceAttributeRepository.save(existingResourceAttribute);
        return convertToDTO(updatedResourceAttribute);
    }
    
    @Transactional
    public void deleteResourceAttribute( Integer id) {
        if (!resourceAttributeRepository.existsById(id)) {
            throw new ResourceNotFoundException("ResourceAttribute", "id", id);
        }
        resourceAttributeRepository.deleteById(id);
    }
    
    private ResourceAttributeDTO convertToDTO(ResourceAttribute resourceAttribute) {
        ResourceAttributeDTO dto = new ResourceAttributeDTO();
        dto.setResourceAttrId(resourceAttribute.getResourceAttrId());
        dto.setResourceId(resourceAttribute.getResource().getResourceId());
        dto.setResourceName(resourceAttribute.getResource().getResourceName());
        dto.setAttributeId(resourceAttribute.getAttribute().getAttributeId());
        dto.setAttributeName(resourceAttribute.getAttribute().getAttributeName());
        dto.setAttributeValue(resourceAttribute.getAttributeValue());
        dto.setValidFrom(resourceAttribute.getValidFrom());
        dto.setValidUntil(resourceAttribute.getValidUntil());
        dto.setCreatedAt(resourceAttribute.getCreatedAt());
        dto.setUpdatedAt(resourceAttribute.getUpdatedAt());
        return dto;
    }
}

package org.oswfm.accesscontrolservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.ResourceDTO;
import org.oswfm.accesscontrolservice.model.entity.Resource;
import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.ResourceRepository;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceService {
    
    private final ResourceRepository resourceRepository;
    private final ACUserRepository userRepository;
    
    @Transactional(readOnly = true)
    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ResourceDTO getResourceById( Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));
        return convertToDTO(resource);
    }
    
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByType(String resourceType) {
        return resourceRepository.findByResourceType(resourceType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ResourceDTO> getActiveResources() {
        return resourceRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ResourceDTO> getResourcesByOwner( Integer ownerId) {
        return resourceRepository.findByOwner_UserId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = convertToEntity(resourceDTO);
        
        if (resourceDTO.getOwnerId() != null) {
            UserEntity owner = userRepository.findById(resourceDTO.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", resourceDTO.getOwnerId()));
            resource.setOwner(owner);
        }
        
        Resource savedResource = resourceRepository.save(resource);
        return convertToDTO(savedResource);
    }
    
    @Transactional
    public ResourceDTO updateResource( Integer id, ResourceDTO resourceDTO) {
        Resource existingResource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource", "id", id));
        
        existingResource.setResourceType(resourceDTO.getResourceType());
        existingResource.setResourceName(resourceDTO.getResourceName());
        existingResource.setResourceUri(resourceDTO.getResourceUri());
        existingResource.setIsActive(resourceDTO.getIsActive());
        
        if (resourceDTO.getOwnerId() != null) {
            UserEntity owner = userRepository.findById(resourceDTO.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", resourceDTO.getOwnerId()));
            existingResource.setOwner(owner);
        } else {
            existingResource.setOwner(null);
        }
        
        Resource updatedResource = resourceRepository.save(existingResource);
        return convertToDTO(updatedResource);
    }
    
    @Transactional
    public void deleteResource( Integer id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource", "id", id);
        }
        resourceRepository.deleteById(id);
    }
    
    private ResourceDTO convertToDTO(Resource resource) {
        ResourceDTO dto = new ResourceDTO();
        dto.setResourceId(resource.getResourceId());
        dto.setResourceType(resource.getResourceType());
        dto.setResourceName(resource.getResourceName());
        dto.setResourceUri(resource.getResourceUri());
        dto.setIsActive(resource.getIsActive());
        dto.setCreatedAt(resource.getCreatedAt());
        dto.setUpdatedAt(resource.getUpdatedAt());
        
        if (resource.getOwner() != null) {
            dto.setOwnerId(resource.getOwner().getUserId());
            dto.setOwnerUsername(resource.getOwner().getUserName());
        }
        
        return dto;
    }
    
    private Resource convertToEntity(ResourceDTO dto) {
        Resource resource = new Resource();
        resource.setResourceType(dto.getResourceType());
        resource.setResourceName(dto.getResourceName());
        resource.setResourceUri(dto.getResourceUri());
        resource.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return resource;
    }
}

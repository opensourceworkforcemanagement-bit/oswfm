package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.AttributeDefinitionDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeDefinitionService {
    
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    
    @Transactional(readOnly = true)
    public List<AttributeDefinitionDTO> getAllAttributeDefinitions() {
        return attributeDefinitionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AttributeDefinitionDTO getAttributeDefinitionById( Integer id) {
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", id));
        return convertToDTO(attributeDefinition);
    }
    
    @Transactional(readOnly = true)
    public AttributeDefinitionDTO getAttributeDefinitionByName(String attributeName) {
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findByAttributeName(attributeName)
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "attributeName", attributeName));
        return convertToDTO(attributeDefinition);
    }
    
    @Transactional(readOnly = true)
    public List<AttributeDefinitionDTO> getAttributeDefinitionsByCategory(String category) {
        return attributeDefinitionRepository.findByAttributeCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AttributeDefinitionDTO createAttributeDefinition(AttributeDefinitionDTO attributeDefinitionDTO) {
        if (attributeDefinitionRepository.existsByAttributeName(attributeDefinitionDTO.getAttributeName())) {
            throw new DuplicateResourceException("AttributeDefinition", "attributeName", attributeDefinitionDTO.getAttributeName());
        }
        
        AttributeDefinition attributeDefinition = convertToEntity(attributeDefinitionDTO);
        AttributeDefinition savedAttributeDefinition = attributeDefinitionRepository.save(attributeDefinition);
        return convertToDTO(savedAttributeDefinition);
    }
    
    @Transactional
    public AttributeDefinitionDTO updateAttributeDefinition( Integer id, AttributeDefinitionDTO attributeDefinitionDTO) {
        AttributeDefinition existingAttributeDefinition = attributeDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", id));
        
        if (!existingAttributeDefinition.getAttributeName().equals(attributeDefinitionDTO.getAttributeName()) && 
            attributeDefinitionRepository.existsByAttributeName(attributeDefinitionDTO.getAttributeName())) {
            throw new DuplicateResourceException("AttributeDefinition", "attributeName", attributeDefinitionDTO.getAttributeName());
        }
        
        existingAttributeDefinition.setAttributeName(attributeDefinitionDTO.getAttributeName());
        existingAttributeDefinition.setAttributeCategory(attributeDefinitionDTO.getAttributeCategory());
        existingAttributeDefinition.setDataType(attributeDefinitionDTO.getDataType());
        existingAttributeDefinition.setDescription(attributeDefinitionDTO.getDescription());
        existingAttributeDefinition.setIsRequired(attributeDefinitionDTO.getIsRequired());
        
        AttributeDefinition updatedAttributeDefinition = attributeDefinitionRepository.save(existingAttributeDefinition);
        return convertToDTO(updatedAttributeDefinition);
    }
    
    @Transactional
    public void deleteAttributeDefinition( Integer id) {
        if (!attributeDefinitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("AttributeDefinition", "id", id);
        }
        attributeDefinitionRepository.deleteById(id);
    }
    
    private AttributeDefinitionDTO convertToDTO(AttributeDefinition attributeDefinition) {
        AttributeDefinitionDTO dto = new AttributeDefinitionDTO();
        dto.setAttributeId(attributeDefinition.getAttributeId());
        dto.setAttributeName(attributeDefinition.getAttributeName());
        dto.setAttributeCategory(attributeDefinition.getAttributeCategory());
        dto.setDataType(attributeDefinition.getDataType());
        dto.setDescription(attributeDefinition.getDescription());
        dto.setIsRequired(attributeDefinition.getIsRequired());
        dto.setCreatedAt(attributeDefinition.getCreatedAt());
        return dto;
    }
    
    private AttributeDefinition convertToEntity(AttributeDefinitionDTO dto) {
        AttributeDefinition attributeDefinition = new AttributeDefinition();
        attributeDefinition.setAttributeName(dto.getAttributeName());
        attributeDefinition.setAttributeCategory(dto.getAttributeCategory());
        attributeDefinition.setDataType(dto.getDataType());
        attributeDefinition.setDescription(dto.getDescription());
        attributeDefinition.setIsRequired(dto.getIsRequired() != null ? dto.getIsRequired() : false);
        return attributeDefinition;
    }
}

package org.oswfm.accesscontrolservice.service;

import org.oswfm.accesscontrolservice.dto.AttributeDefinitionDTO;
import org.oswfm.accesscontrolservice.exception.DuplicateResourceException;
import org.oswfm.accesscontrolservice.model.entity.AttributeCategory;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.accesscontrolservice.model.entity.DataType;
import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.repository.AttributeCategoryRepository;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import org.oswfm.accesscontrolservice.repository.DataTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeDefinitionService {

    private final AttributeDefinitionRepository attributeDefinitionRepository;
    private final AttributeCategoryRepository attributeCategoryRepository;
    private final DataTypeRepository dataTypeRepository;

    @Transactional(readOnly = true)
    public List<AttributeDefinitionDTO> getAllAttributeDefinitions() {
        return attributeDefinitionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AttributeDefinitionDTO getAttributeDefinitionById(Integer id) {
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
    public List<AttributeDefinitionDTO> getAttributeDefinitionsByCategory(Integer attributeCategoryId) {
        return attributeDefinitionRepository.findByAttributeCategory_AttributeCategoryId(attributeCategoryId).stream()
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
    public AttributeDefinitionDTO updateAttributeDefinition(Integer id, AttributeDefinitionDTO attributeDefinitionDTO) {
        AttributeDefinition existingAttributeDefinition = attributeDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", id));

        if (!existingAttributeDefinition.getAttributeName().equals(attributeDefinitionDTO.getAttributeName()) &&
            attributeDefinitionRepository.existsByAttributeName(attributeDefinitionDTO.getAttributeName())) {
            throw new DuplicateResourceException("AttributeDefinition", "attributeName", attributeDefinitionDTO.getAttributeName());
        }

        AttributeCategory category = attributeCategoryRepository.findById(attributeDefinitionDTO.getAttributeCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeCategory", "id", attributeDefinitionDTO.getAttributeCategoryId()));
        DataType dataType = dataTypeRepository.findById(attributeDefinitionDTO.getDataTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("DataType", "id", attributeDefinitionDTO.getDataTypeId()));

        existingAttributeDefinition.setAttributeName(attributeDefinitionDTO.getAttributeName());
        existingAttributeDefinition.setAttributeCategory(category);
        existingAttributeDefinition.setDataType(dataType);
        existingAttributeDefinition.setDescription(attributeDefinitionDTO.getDescription());
        existingAttributeDefinition.setIsRequired(attributeDefinitionDTO.getIsRequired());

        AttributeDefinition updatedAttributeDefinition = attributeDefinitionRepository.save(existingAttributeDefinition);
        return convertToDTO(updatedAttributeDefinition);
    }

    @Transactional
    public void deleteAttributeDefinition(Integer id) {
        if (!attributeDefinitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("AttributeDefinition", "id", id);
        }
        attributeDefinitionRepository.deleteById(id);
    }

    private AttributeDefinitionDTO convertToDTO(AttributeDefinition attributeDefinition) {
        AttributeDefinitionDTO dto = new AttributeDefinitionDTO();
        dto.setAttributeId(attributeDefinition.getAttributeId());
        dto.setAttributeName(attributeDefinition.getAttributeName());
        dto.setAttributeCategoryId(attributeDefinition.getAttributeCategory().getAttributeCategoryId());
        dto.setAttributeCategoryName(attributeDefinition.getAttributeCategory().getCategoryName());
        dto.setDataTypeId(attributeDefinition.getDataType().getDataTypeId());
        dto.setDataTypeName(attributeDefinition.getDataType().getTypeName());
        dto.setDescription(attributeDefinition.getDescription());
        dto.setIsRequired(attributeDefinition.getIsRequired());
        dto.setCreatedAt(attributeDefinition.getCreatedAt());
        return dto;
    }

    private AttributeDefinition convertToEntity(AttributeDefinitionDTO dto) {
        AttributeDefinition attributeDefinition = new AttributeDefinition();

        AttributeCategory category = attributeCategoryRepository.findById(dto.getAttributeCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeCategory", "id", dto.getAttributeCategoryId()));
        DataType dataType = dataTypeRepository.findById(dto.getDataTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("DataType", "id", dto.getDataTypeId()));

        attributeDefinition.setAttributeName(dto.getAttributeName());
        attributeDefinition.setAttributeCategory(category);
        attributeDefinition.setDataType(dataType);
        attributeDefinition.setDescription(dto.getDescription());
        attributeDefinition.setIsRequired(dto.getIsRequired() != null ? dto.getIsRequired() : false);
        return attributeDefinition;
    }
}

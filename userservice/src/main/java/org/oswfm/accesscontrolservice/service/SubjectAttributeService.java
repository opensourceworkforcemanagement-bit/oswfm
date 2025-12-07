package org.oswfm.accesscontrolservice.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.SubjectAttributeDTO;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;
import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import org.oswfm.accesscontrolservice.repository.SubjectAttributeRepository;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectAttributeService {
    
    private final SubjectAttributeRepository subjectAttributeRepository;
    private final ACUserRepository userRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    
    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getAllSubjectAttributes() {
        return subjectAttributeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public SubjectAttributeDTO getSubjectAttributeById( Integer id) {
        SubjectAttribute subjectAttribute = subjectAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", id));
        return convertToDTO(subjectAttribute);
    }
    
    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getSubjectAttributesByUserId( Integer userId) {
        return subjectAttributeRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getActiveSubjectAttributesByUserId( Integer userId) {
        return subjectAttributeRepository.findActiveAttributesByUserId(userId, OffsetDateTime.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public SubjectAttributeDTO createSubjectAttribute(SubjectAttributeDTO subjectAttributeDTO) {
        UserEntity user = userRepository.findById(subjectAttributeDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", subjectAttributeDTO.getUserId()));
        
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findById(subjectAttributeDTO.getAttributeId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", subjectAttributeDTO.getAttributeId()));
        
        SubjectAttribute subjectAttribute = new SubjectAttribute();
        subjectAttribute.setUser(user);
        subjectAttribute.setAttribute(attributeDefinition);
        subjectAttribute.setAttributeValue(subjectAttributeDTO.getAttributeValue());
        subjectAttribute.setValidFrom(subjectAttributeDTO.getValidFrom() != null ? 
                subjectAttributeDTO.getValidFrom() : OffsetDateTime.now());
        subjectAttribute.setValidUntil(subjectAttributeDTO.getValidUntil());
        
        SubjectAttribute savedSubjectAttribute = subjectAttributeRepository.save(subjectAttribute);
        return convertToDTO(savedSubjectAttribute);
    }
    
    @Transactional
    public SubjectAttributeDTO updateSubjectAttribute( Integer id, SubjectAttributeDTO subjectAttributeDTO) {
        SubjectAttribute existingSubjectAttribute = subjectAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", id));
        
        existingSubjectAttribute.setAttributeValue(subjectAttributeDTO.getAttributeValue());
        existingSubjectAttribute.setValidFrom(subjectAttributeDTO.getValidFrom());
        existingSubjectAttribute.setValidUntil(subjectAttributeDTO.getValidUntil());
        
        SubjectAttribute updatedSubjectAttribute = subjectAttributeRepository.save(existingSubjectAttribute);
        return convertToDTO(updatedSubjectAttribute);
    }
    
    @Transactional
    public void deleteSubjectAttribute( Integer id) {
        if (!subjectAttributeRepository.existsById(id)) {
            throw new ResourceNotFoundException("SubjectAttribute", "id", id);
        }
        subjectAttributeRepository.deleteById(id);
    }
    
    private SubjectAttributeDTO convertToDTO(SubjectAttribute subjectAttribute) {
        SubjectAttributeDTO dto = new SubjectAttributeDTO();
        dto.setSubjectAttrId(subjectAttribute.getSubjectAttrId());
        dto.setUserId(subjectAttribute.getUser().getUserId());
        dto.setUsername(subjectAttribute.getUser().getUserName());
        dto.setAttributeId(subjectAttribute.getAttribute().getAttributeId());
        dto.setAttributeName(subjectAttribute.getAttribute().getAttributeName());
        dto.setAttributeValue(subjectAttribute.getAttributeValue());
        dto.setValidFrom(subjectAttribute.getValidFrom());
        dto.setValidUntil(subjectAttribute.getValidUntil());
        dto.setCreatedAt(subjectAttribute.getCreatedAt());
        dto.setUpdatedAt(subjectAttribute.getUpdatedAt());
        return dto;
    }
}

package org.oswfm.accesscontrolservice.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.oswfm.ResourceNotFoundException;
import org.oswfm.accesscontrolservice.dto.GroupSubjectAttributeDTO;
import org.oswfm.accesscontrolservice.dto.SubjectAttributeDTO;
import org.oswfm.accesscontrolservice.dto.UserSubjectAttributeDTO;
import org.oswfm.accesscontrolservice.model.entity.AttributeDefinition;
import org.oswfm.accesscontrolservice.model.entity.GroupSubjectAttribute;
import org.oswfm.accesscontrolservice.model.entity.SubjectAttribute;
import org.oswfm.accesscontrolservice.model.entity.UserGroup;
import org.oswfm.accesscontrolservice.model.entity.UserGroupMembership;
import org.oswfm.accesscontrolservice.model.entity.UserSubjectAttribute;
import org.oswfm.accesscontrolservice.repository.ACUserRepository;
import org.oswfm.accesscontrolservice.repository.AttributeDefinitionRepository;
import org.oswfm.accesscontrolservice.repository.GroupSubjectAttributeRepository;
import org.oswfm.accesscontrolservice.repository.SubjectAttributeRepository;
import org.oswfm.accesscontrolservice.repository.UserGroupMembershipRepository;
import org.oswfm.accesscontrolservice.repository.UserGroupRepository;
import org.oswfm.accesscontrolservice.repository.UserSubjectAttributeRepository;
import org.oswfm.commons.model.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectAttributeService {

    private final SubjectAttributeRepository subjectAttributeRepository;
    private final UserSubjectAttributeRepository userSubjectAttributeRepository;
    private final GroupSubjectAttributeRepository groupSubjectAttributeRepository;
    private final UserGroupMembershipRepository userGroupMembershipRepository;
    private final UserGroupRepository userGroupRepository;
    private final ACUserRepository userRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;

    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getAllSubjectAttributes() {
        return subjectAttributeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getSubjectAttributesByAttributeName(String attributeName) {
        AttributeDefinition definition = attributeDefinitionRepository.findByAttributeName(attributeName)
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "attributeName", attributeName));
        return subjectAttributeRepository.findByAttribute_AttributeId(definition.getAttributeId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectAttributeDTO getSubjectAttributeById(Integer id) {
        SubjectAttribute subjectAttribute = subjectAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", id));
        return convertToDTO(subjectAttribute);
    }

    /**
     * Get all resolved attributes for a user (direct + group assignments, active only)
     */
    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getResolvedAttributesByUserId(Integer userId) {
        OffsetDateTime now = OffsetDateTime.now();

        // Direct assignments
        List<UserSubjectAttribute> directAssignments =
                userSubjectAttributeRepository.findByUser_UserId(userId);
        List<Integer> directAttrIds = directAssignments.stream()
                .map(usa -> usa.getSubjectAttribute().getSubjectAttrId())
                .collect(Collectors.toList());

        // Group assignments
        List<UserGroupMembership> memberships =
                userGroupMembershipRepository.findByUser_UserId(userId);
        List<Integer> groupIds = memberships.stream()
                .map(m -> m.getGroup().getGroupId())
                .collect(Collectors.toList());

        List<Integer> groupAttrIds = new ArrayList<>();
        if (!groupIds.isEmpty()) {
            List<GroupSubjectAttribute> groupAssignments =
                    groupSubjectAttributeRepository.findByGroup_GroupIdIn(groupIds);
            groupAttrIds = groupAssignments.stream()
                    .map(gsa -> gsa.getSubjectAttribute().getSubjectAttrId())
                    .collect(Collectors.toList());
        }

        List<Integer> allAttrIds = new ArrayList<>();
        allAttrIds.addAll(directAttrIds);
        allAttrIds.addAll(groupAttrIds);

        if (allAttrIds.isEmpty()) {
            return List.of();
        }

        return subjectAttributeRepository.findActiveByIds(allAttrIds, now).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get attributes assigned to a specific group
     */
    @Transactional(readOnly = true)
    public List<SubjectAttributeDTO> getAttributesByGroupId(Integer groupId) {
        return groupSubjectAttributeRepository.findByGroup_GroupId(groupId).stream()
                .map(gsa -> convertToDTO(gsa.getSubjectAttribute()))
                .collect(Collectors.toList());
    }

    @Transactional
    public SubjectAttributeDTO createSubjectAttribute(SubjectAttributeDTO subjectAttributeDTO) {
        AttributeDefinition attributeDefinition = attributeDefinitionRepository.findById(subjectAttributeDTO.getAttributeId())
                .orElseThrow(() -> new ResourceNotFoundException("AttributeDefinition", "id", subjectAttributeDTO.getAttributeId()));

        SubjectAttribute subjectAttribute = new SubjectAttribute();
        subjectAttribute.setAttribute(attributeDefinition);
        subjectAttribute.setAttributeValue(subjectAttributeDTO.getAttributeValue());
        subjectAttribute.setValidFrom(subjectAttributeDTO.getValidFrom() != null ?
                subjectAttributeDTO.getValidFrom() : OffsetDateTime.now());
        subjectAttribute.setValidUntil(subjectAttributeDTO.getValidUntil());

        SubjectAttribute savedSubjectAttribute = subjectAttributeRepository.save(subjectAttribute);
        return convertToDTO(savedSubjectAttribute);
    }

    @Transactional
    public SubjectAttributeDTO updateSubjectAttribute(Integer id, SubjectAttributeDTO subjectAttributeDTO) {
        SubjectAttribute existingSubjectAttribute = subjectAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", id));

        existingSubjectAttribute.setAttributeValue(subjectAttributeDTO.getAttributeValue());
        existingSubjectAttribute.setValidFrom(subjectAttributeDTO.getValidFrom());
        existingSubjectAttribute.setValidUntil(subjectAttributeDTO.getValidUntil());

        SubjectAttribute updatedSubjectAttribute = subjectAttributeRepository.save(existingSubjectAttribute);
        return convertToDTO(updatedSubjectAttribute);
    }

    @Transactional
    public void deleteSubjectAttribute(Integer id) {
        if (!subjectAttributeRepository.existsById(id)) {
            throw new ResourceNotFoundException("SubjectAttribute", "id", id);
        }
        subjectAttributeRepository.deleteById(id);
    }

    /**
     * Assign a subject attribute directly to a user
     */
    @Transactional
    public UserSubjectAttributeDTO assignAttributeToUser(UserSubjectAttributeDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
        SubjectAttribute subjectAttribute = subjectAttributeRepository.findById(dto.getSubjectAttrId())
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", dto.getSubjectAttrId()));

        UserSubjectAttribute assignment = new UserSubjectAttribute();
        assignment.setUser(user);
        assignment.setSubjectAttribute(subjectAttribute);

        UserSubjectAttribute saved = userSubjectAttributeRepository.save(assignment);
        return convertToUserSubjectAttributeDTO(saved);
    }

    /**
     * Remove a direct attribute assignment from a user
     */
    @Transactional
    public void removeAttributeFromUser(Integer userId, Integer subjectAttrId) {
        userSubjectAttributeRepository.deleteByUser_UserIdAndSubjectAttribute_SubjectAttrId(userId, subjectAttrId);
    }

    /**
     * Assign a subject attribute to a group
     */
    @Transactional
    public GroupSubjectAttributeDTO assignAttributeToGroup(GroupSubjectAttributeDTO dto) {
        UserGroup group = userGroupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("UserGroup", "id", dto.getGroupId()));
        SubjectAttribute subjectAttribute = subjectAttributeRepository.findById(dto.getSubjectAttrId())
                .orElseThrow(() -> new ResourceNotFoundException("SubjectAttribute", "id", dto.getSubjectAttrId()));

        GroupSubjectAttribute assignment = new GroupSubjectAttribute();
        assignment.setGroup(group);
        assignment.setSubjectAttribute(subjectAttribute);

        GroupSubjectAttribute saved = groupSubjectAttributeRepository.save(assignment);
        return convertToGroupSubjectAttributeDTO(saved);
    }

    /**
     * Remove an attribute assignment from a group
     */
    @Transactional
    public void removeAttributeFromGroup(Integer groupId, Integer subjectAttrId) {
        groupSubjectAttributeRepository.deleteByGroup_GroupIdAndSubjectAttribute_SubjectAttrId(groupId, subjectAttrId);
    }

    private SubjectAttributeDTO convertToDTO(SubjectAttribute subjectAttribute) {
        SubjectAttributeDTO dto = new SubjectAttributeDTO();
        dto.setSubjectAttrId(subjectAttribute.getSubjectAttrId());
        dto.setAttributeId(subjectAttribute.getAttribute().getAttributeId());
        dto.setAttributeName(subjectAttribute.getAttribute().getAttributeName());
        dto.setAttributeValue(subjectAttribute.getAttributeValue());
        dto.setValidFrom(subjectAttribute.getValidFrom());
        dto.setValidUntil(subjectAttribute.getValidUntil());
        dto.setCreatedAt(subjectAttribute.getCreatedAt());
        dto.setUpdatedAt(subjectAttribute.getUpdatedAt());
        return dto;
    }

    private UserSubjectAttributeDTO convertToUserSubjectAttributeDTO(UserSubjectAttribute usa) {
        UserSubjectAttributeDTO dto = new UserSubjectAttributeDTO();
        dto.setId(usa.getId());
        dto.setUserId(usa.getUser().getUserId());
        dto.setUsername(usa.getUser().getUserName());
        dto.setSubjectAttrId(usa.getSubjectAttribute().getSubjectAttrId());
        dto.setAttributeName(usa.getSubjectAttribute().getAttribute().getAttributeName());
        dto.setAttributeValue(usa.getSubjectAttribute().getAttributeValue());
        dto.setCreatedAt(usa.getCreatedAt());
        return dto;
    }

    private GroupSubjectAttributeDTO convertToGroupSubjectAttributeDTO(GroupSubjectAttribute gsa) {
        GroupSubjectAttributeDTO dto = new GroupSubjectAttributeDTO();
        dto.setId(gsa.getId());
        dto.setGroupId(gsa.getGroup().getGroupId());
        dto.setGroupName(gsa.getGroup().getGroupName());
        dto.setSubjectAttrId(gsa.getSubjectAttribute().getSubjectAttrId());
        dto.setAttributeName(gsa.getSubjectAttribute().getAttribute().getAttributeName());
        dto.setAttributeValue(gsa.getSubjectAttribute().getAttributeValue());
        dto.setCreatedAt(gsa.getCreatedAt());
        return dto;
    }
}

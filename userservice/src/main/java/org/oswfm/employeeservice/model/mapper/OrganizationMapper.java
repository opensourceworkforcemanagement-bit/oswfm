package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Organization;
import org.oswfm.employeeservice.model.dto.OrganizationRequestDTO;
import org.oswfm.employeeservice.model.dto.OrganizationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public Organization toEntity(OrganizationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Organization entity = new Organization();
        entity.setParentOrganizationId(dto.getParentOrganizationId());
        entity.setOrganization(dto.getOrganization());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public OrganizationResponseDTO toResponseDTO(Organization entity) {
        if (entity == null) {
            return null;
        }
        
        OrganizationResponseDTO dto = new OrganizationResponseDTO();
        dto.setOrganizationId(entity.getOrganizationId());
        dto.setParentOrganizationId(entity.getParentOrganizationId());
        dto.setOrganization(entity.getOrganization());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(OrganizationRequestDTO dto, Organization entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setParentOrganizationId(dto.getParentOrganizationId());
        entity.setOrganization(dto.getOrganization());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

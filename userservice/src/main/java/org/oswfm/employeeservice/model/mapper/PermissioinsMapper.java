package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Permissioins;
import org.oswfm.employeeservice.model.dto.PermissioinsRequestDTO;
import org.oswfm.employeeservice.model.dto.PermissioinsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PermissioinsMapper {

    public Permissioins toEntity(PermissioinsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Permissioins entity = new Permissioins();
        entity.setPermissionTag(dto.getPermissionTag());
        entity.setPermissionName(dto.getPermissionName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public PermissioinsResponseDTO toResponseDTO(Permissioins entity) {
        if (entity == null) {
            return null;
        }
        
        PermissioinsResponseDTO dto = new PermissioinsResponseDTO();
        dto.setPermissioinsId(entity.getPermissioinsId());
        dto.setPermissionTag(entity.getPermissionTag());
        dto.setPermissionName(entity.getPermissionName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public void updateEntityFromDTO(PermissioinsRequestDTO dto, Permissioins entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setPermissionTag(dto.getPermissionTag());
        entity.setPermissionName(dto.getPermissionName());
        entity.setDescription(dto.getDescription());
    }
}

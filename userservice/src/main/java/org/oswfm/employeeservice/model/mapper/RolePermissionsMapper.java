package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.RolePermissions;
import org.oswfm.employeeservice.model.dto.RolePermissionsRequestDTO;
import org.oswfm.employeeservice.model.dto.RolePermissionsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionsMapper {

    public RolePermissions toEntity(RolePermissionsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        RolePermissions entity = new RolePermissions();
        entity.setEmployeeRoleId(dto.getEmployeeRoleId());
        entity.setPermissioinsId(dto.getPermissioinsId());
        return entity;
    }

    public RolePermissionsResponseDTO toResponseDTO(RolePermissions entity) {
        if (entity == null) {
            return null;
        }
        
        RolePermissionsResponseDTO dto = new RolePermissionsResponseDTO();
        dto.setRolePermissionId(entity.getRolePermissionId());
        dto.setEmployeeRoleId(entity.getEmployeeRoleId());
        dto.setPermissioinsId(entity.getPermissioinsId());
        return dto;
    }

    public void updateEntityFromDTO(RolePermissionsRequestDTO dto, RolePermissions entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeRoleId(dto.getEmployeeRoleId());
        entity.setPermissioinsId(dto.getPermissioinsId());
    }
}

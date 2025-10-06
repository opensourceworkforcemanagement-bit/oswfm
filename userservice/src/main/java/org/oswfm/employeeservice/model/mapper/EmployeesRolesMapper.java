package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesRoles;
import org.oswfm.employeeservice.model.dto.EmployeesRolesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesRolesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesRolesMapper {

    public EmployeesRoles toEntity(EmployeesRolesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesRoles entity = new EmployeesRoles();
        entity.setRole(dto.getRole());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public EmployeesRolesResponseDTO toResponseDTO(EmployeesRoles entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesRolesResponseDTO dto = new EmployeesRolesResponseDTO();
        dto.setEmployeeRoleId(entity.getEmployeeRoleId());
        dto.setRole(entity.getRole());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesRolesRequestDTO dto, EmployeesRoles entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setRole(dto.getRole());
        entity.setDescription(dto.getDescription());
    }
}

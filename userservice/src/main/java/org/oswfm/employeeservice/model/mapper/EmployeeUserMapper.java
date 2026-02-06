package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeeUser;
import org.oswfm.employeeservice.model.dto.EmployeeUserRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeUserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUserMapper {

    public EmployeeUser toEntity(EmployeeUserRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        EmployeeUser entity = new EmployeeUser();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    public EmployeeUserResponseDTO toResponseDTO(EmployeeUser entity) {
        if (entity == null) {
            return null;
        }

        EmployeeUserResponseDTO dto = new EmployeeUserResponseDTO();
        dto.setEmployeeUserId(entity.getEmployeeUserId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeeUserRequestDTO dto, EmployeeUser entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setUserId(dto.getUserId());
    }
}

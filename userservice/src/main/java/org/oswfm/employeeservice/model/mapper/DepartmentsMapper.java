package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Departments;
import org.oswfm.employeeservice.model.dto.DepartmentsRequestDTO;
import org.oswfm.employeeservice.model.dto.DepartmentsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DepartmentsMapper {

    public Departments toEntity(DepartmentsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Departments entity = new Departments();
        entity.setDepartment(dto.getDepartment());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public DepartmentsResponseDTO toResponseDTO(Departments entity) {
        if (entity == null) {
            return null;
        }
        
        DepartmentsResponseDTO dto = new DepartmentsResponseDTO();
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setDepartment(entity.getDepartment());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(DepartmentsRequestDTO dto, Departments entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setDepartment(dto.getDepartment());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

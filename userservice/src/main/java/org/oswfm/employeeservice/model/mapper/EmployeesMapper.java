package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Employees;
import org.oswfm.employeeservice.model.dto.EmployeesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesMapper {

    public Employees toEntity(EmployeesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Employees entity = new Employees();
        entity.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setStatus(dto.getStatus());
        entity.setUserName(dto.getUserName());
        return entity;
    }

    public EmployeesResponseDTO toResponseDTO(Employees entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesResponseDTO dto = new EmployeesResponseDTO();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmployeeIdentifier(entity.getEmployeeIdentifier());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setStatus(entity.getStatus());
        dto.setUserName(entity.getUserName());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesRequestDTO dto, Employees entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setStatus(dto.getStatus());
        entity.setUserName(dto.getUserName());
    }
}

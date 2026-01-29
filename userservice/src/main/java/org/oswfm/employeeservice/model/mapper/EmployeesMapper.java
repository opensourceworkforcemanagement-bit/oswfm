package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.dto.EmployeesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesResponseDTO;
import org.oswfm.employeeservice.model.entity.EmployeeDetails;
import org.springframework.stereotype.Component;

@Component
public class EmployeesMapper {

    public EmployeeDetails toEntity(EmployeesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeeDetails entity = new EmployeeDetails();
        entity.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setStatus(dto.getStatus());
        entity.setUserName(dto.getUserName());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    public EmployeesResponseDTO toResponseDTO(EmployeeDetails entity) {
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
        dto.setUserId(entity.getUserId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesRequestDTO dto, EmployeeDetails entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeIdentifier(dto.getEmployeeIdentifier());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setStatus(dto.getStatus());
        entity.setUserName(dto.getUserName());
        entity.setUserId(dto.getUserId());
    }
}

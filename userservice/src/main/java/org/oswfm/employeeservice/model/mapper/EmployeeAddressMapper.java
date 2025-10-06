package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeeAddress;
import org.oswfm.employeeservice.model.dto.EmployeeAddressRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeeAddressResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAddressMapper {

    public EmployeeAddress toEntity(EmployeeAddressRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeeAddress entity = new EmployeeAddress();
        entity.setEmployeeId(dto.getEmployeeId());
        return entity;
    }

    public EmployeeAddressResponseDTO toResponseDTO(EmployeeAddress entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeeAddressResponseDTO dto = new EmployeeAddressResponseDTO();
        dto.setAddressId(entity.getAddressId());
        dto.setEmployeeId(entity.getEmployeeId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeeAddressRequestDTO dto, EmployeeAddress entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
    }
}

package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesSsn;
import org.oswfm.employeeservice.model.dto.EmployeesSsnRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSsnResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesSsnMapper {

    public EmployeesSsn toEntity(EmployeesSsnRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesSsn entity = new EmployeesSsn();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setSsn(dto.getSsn());
        return entity;
    }

    public EmployeesSsnResponseDTO toResponseDTO(EmployeesSsn entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesSsnResponseDTO dto = new EmployeesSsnResponseDTO();
        dto.setEmployeeSsnId(entity.getEmployeeSsnId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setSsn(entity.getSsn());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesSsnRequestDTO dto, EmployeesSsn entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setSsn(dto.getSsn());
    }
}

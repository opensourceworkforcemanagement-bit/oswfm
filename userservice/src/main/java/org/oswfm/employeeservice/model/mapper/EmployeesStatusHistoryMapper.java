package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesStatusHistory;
import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesStatusHistoryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesStatusHistoryMapper {

    public EmployeesStatusHistory toEntity(EmployeesStatusHistoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesStatusHistory entity = new EmployeesStatusHistory();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setStatus(dto.getStatus());
        entity.setChangedAt(dto.getChangedAt());
        entity.setChangedByEmployeeId(dto.getChangedByEmployeeId());
        return entity;
    }

    public EmployeesStatusHistoryResponseDTO toResponseDTO(EmployeesStatusHistory entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesStatusHistoryResponseDTO dto = new EmployeesStatusHistoryResponseDTO();
        dto.setEmployeeStatusHistoryId(entity.getEmployeeStatusHistoryId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setStatus(entity.getStatus());
        dto.setChangedAt(entity.getChangedAt());
        dto.setChangedByEmployeeId(entity.getChangedByEmployeeId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesStatusHistoryRequestDTO dto, EmployeesStatusHistory entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setStatus(dto.getStatus());
        entity.setChangedAt(dto.getChangedAt());
        entity.setChangedByEmployeeId(dto.getChangedByEmployeeId());
    }
}

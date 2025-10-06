package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesAuditLog;
import org.oswfm.employeeservice.model.dto.EmployeesAuditLogRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesAuditLogResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesAuditLogMapper {

    public EmployeesAuditLog toEntity(EmployeesAuditLogRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesAuditLog entity = new EmployeesAuditLog();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setAction(dto.getAction());
        entity.setActionTimestamp(dto.getActionTimestamp());
        entity.setActionBy(dto.getActionBy());
        return entity;
    }

    public EmployeesAuditLogResponseDTO toResponseDTO(EmployeesAuditLog entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesAuditLogResponseDTO dto = new EmployeesAuditLogResponseDTO();
        dto.setEmployeeAuditLogId(entity.getEmployeeAuditLogId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setAction(entity.getAction());
        dto.setActionTimestamp(entity.getActionTimestamp());
        dto.setActionBy(entity.getActionBy());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesAuditLogRequestDTO dto, EmployeesAuditLog entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setAction(dto.getAction());
        entity.setActionTimestamp(dto.getActionTimestamp());
        entity.setActionBy(dto.getActionBy());
    }
}

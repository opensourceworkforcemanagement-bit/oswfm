package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLog;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetAuditLogMapper {

    public TimesheetAuditLog toEntity(TimesheetAuditLogRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetAuditLog entity = new TimesheetAuditLog();
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    public TimesheetAuditLogResponseDTO toResponseDTO(TimesheetAuditLog entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetAuditLogResponseDTO dto = new TimesheetAuditLogResponseDTO();
        dto.setTimesheetAuditLogId(entity.getTimesheetAuditLogId());
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetAuditLogRequestDTO dto, TimesheetAuditLog entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
    }
}

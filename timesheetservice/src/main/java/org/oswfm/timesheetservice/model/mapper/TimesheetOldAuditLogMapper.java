package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetOldAuditLog;
import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOldAuditLogResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetOldAuditLogMapper {

    public TimesheetOldAuditLog toEntity(TimesheetOldAuditLogRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetOldAuditLog entity = new TimesheetOldAuditLog();
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    public TimesheetOldAuditLogResponseDTO toResponseDTO(TimesheetOldAuditLog entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetOldAuditLogResponseDTO dto = new TimesheetOldAuditLogResponseDTO();
        dto.setTimesheetAuditLogId(entity.getTimesheetAuditLogId());
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetOldAuditLogRequestDTO dto, TimesheetOldAuditLog entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
    }
}

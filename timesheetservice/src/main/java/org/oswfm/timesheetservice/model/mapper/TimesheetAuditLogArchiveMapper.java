package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetAuditLogArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetAuditLogArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetAuditLogArchiveMapper {

    public TimesheetAuditLogArchive toEntity(TimesheetAuditLogArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetAuditLogArchive entity = new TimesheetAuditLogArchive();
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setOperationTypeId(dto.getOperationTypeId());
        return entity;
    }

    public TimesheetAuditLogArchiveResponseDTO toResponseDTO(TimesheetAuditLogArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetAuditLogArchiveResponseDTO dto = new TimesheetAuditLogArchiveResponseDTO();
        dto.setTimesheetAuditLogId(entity.getTimesheetAuditLogId());
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setOperationTypeId(entity.getOperationTypeId());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetAuditLogArchiveRequestDTO dto, TimesheetAuditLogArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setOperationTypeId(dto.getOperationTypeId());
    }
}

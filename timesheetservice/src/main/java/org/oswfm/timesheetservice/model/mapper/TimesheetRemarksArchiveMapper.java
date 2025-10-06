package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetRemarksArchiveMapper {

    public TimesheetRemarksArchive toEntity(TimesheetRemarksArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetRemarksArchive entity = new TimesheetRemarksArchive();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRemarks(dto.getRemarks());
        entity.setRemarksOrder(dto.getRemarksOrder());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    public TimesheetRemarksArchiveResponseDTO toResponseDTO(TimesheetRemarksArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetRemarksArchiveResponseDTO dto = new TimesheetRemarksArchiveResponseDTO();
        dto.setTimesheetRemarksId(entity.getTimesheetRemarksId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksOrder(entity.getRemarksOrder());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetRemarksArchiveRequestDTO dto, TimesheetRemarksArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRemarks(dto.getRemarks());
        entity.setRemarksOrder(dto.getRemarksOrder());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
    }
}

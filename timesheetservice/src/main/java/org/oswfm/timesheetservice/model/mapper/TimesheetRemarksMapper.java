package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarks;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetRemarksMapper {

    public TimesheetRemarks toEntity(TimesheetRemarksRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetRemarks entity = new TimesheetRemarks();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRemarks(dto.getRemarks());
        entity.setRemarksOrder(dto.getRemarksOrder());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    public TimesheetRemarksResponseDTO toResponseDTO(TimesheetRemarks entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetRemarksResponseDTO dto = new TimesheetRemarksResponseDTO();
        dto.setTimesheetRemarksId(entity.getTimesheetRemarksId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksOrder(entity.getRemarksOrder());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetRemarksRequestDTO dto, TimesheetRemarks entity) {
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

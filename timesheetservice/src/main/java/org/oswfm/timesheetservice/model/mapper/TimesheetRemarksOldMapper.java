package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetRemarksOld;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetRemarksOldResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetRemarksOldMapper {

    public TimesheetRemarksOld toEntity(TimesheetRemarksOldRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetRemarksOld entity = new TimesheetRemarksOld();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRemarks(dto.getRemarks());
        entity.setRemarksOrder(dto.getRemarksOrder());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    public TimesheetRemarksOldResponseDTO toResponseDTO(TimesheetRemarksOld entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetRemarksOldResponseDTO dto = new TimesheetRemarksOldResponseDTO();
        dto.setTimesheetRemarksId(entity.getTimesheetRemarksId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setRemarks(entity.getRemarks());
        dto.setRemarksOrder(entity.getRemarksOrder());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetRemarksOldRequestDTO dto, TimesheetRemarksOld entity) {
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

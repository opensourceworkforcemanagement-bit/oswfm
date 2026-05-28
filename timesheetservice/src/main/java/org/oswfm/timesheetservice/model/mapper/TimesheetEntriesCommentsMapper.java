package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntriesComments;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesCommentsMapper {

    public TimesheetEntriesComments toEntity(TimesheetEntriesCommentsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesComments entity = new TimesheetEntriesComments();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
        return entity;
    }

    public TimesheetEntriesCommentsResponseDTO toResponseDTO(TimesheetEntriesComments entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetEntriesCommentsResponseDTO dto = new TimesheetEntriesCommentsResponseDTO();
        dto.setTimesheetEntriesCommentsId(entity.getTimesheetEntriesCommentsId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setEntryDay(entity.getEntryDay());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesCommentsRequestDTO dto, TimesheetEntriesComments entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
    }
}

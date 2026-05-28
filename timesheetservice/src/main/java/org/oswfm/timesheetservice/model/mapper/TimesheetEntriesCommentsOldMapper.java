package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsOld;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesCommentsOldMapper {

    public TimesheetEntriesCommentsOld toEntity(TimesheetEntriesCommentsOldRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesCommentsOld entity = new TimesheetEntriesCommentsOld();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
        return entity;
    }

    public TimesheetEntriesCommentsOldResponseDTO toResponseDTO(TimesheetEntriesCommentsOld entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetEntriesCommentsOldResponseDTO dto = new TimesheetEntriesCommentsOldResponseDTO();
        dto.setTimesheetEntriesCommentsId(entity.getTimesheetEntriesCommentsId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setEntryDay(entity.getEntryDay());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesCommentsOldRequestDTO dto, TimesheetEntriesCommentsOld entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
    }
}

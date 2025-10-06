package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsOld;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsOldResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesCommentsOldMapper {

    public TimesheetEntriesCommentsOld toEntity(TimesheetEntriesCommentsOldRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesCommentsOld entity = new TimesheetEntriesCommentsOld();
        entity.setTimesheeId(dto.getTimesheeId());
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
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setEntryDay(entity.getEntryDay());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesCommentsOldRequestDTO dto, TimesheetEntriesCommentsOld entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
    }
}

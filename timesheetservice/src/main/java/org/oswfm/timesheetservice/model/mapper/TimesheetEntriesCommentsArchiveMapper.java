package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesCommentsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesCommentsArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesCommentsArchiveMapper {

    public TimesheetEntriesCommentsArchive toEntity(TimesheetEntriesCommentsArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesCommentsArchive entity = new TimesheetEntriesCommentsArchive();
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
        return entity;
    }

    public TimesheetEntriesCommentsArchiveResponseDTO toResponseDTO(TimesheetEntriesCommentsArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetEntriesCommentsArchiveResponseDTO dto = new TimesheetEntriesCommentsArchiveResponseDTO();
        dto.setTimesheetEntriesCommentsId(entity.getTimesheetEntriesCommentsId());
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setEntryDay(entity.getEntryDay());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesCommentsArchiveRequestDTO dto, TimesheetEntriesCommentsArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheeId(dto.getTimesheeId());
        entity.setEntryDay(dto.getEntryDay());
        entity.setComments(dto.getComments());
    }
}

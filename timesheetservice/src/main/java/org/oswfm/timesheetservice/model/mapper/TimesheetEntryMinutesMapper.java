package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntryMinutesResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryMinutes;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntryMinutesMapper {

    public TimesheetEntryMinutes toEntity(TimesheetEntryMinutesRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        TimesheetEntryMinutes entity = new TimesheetEntryMinutes();
        entity.setTimesheetEntriesId(dto.getTimesheetEntriesId());
        entity.setMinutes(dto.getMinutes());
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setDate(dto.getDate());
        return entity;
    }

    public TimesheetEntryMinutesResponseDTO toResponseDTO(TimesheetEntryMinutes entity) {
        if (entity == null) {
            return null;
        }

        TimesheetEntryMinutesResponseDTO dto = new TimesheetEntryMinutesResponseDTO();
        dto.setId(entity.getId());
        dto.setTimesheetEntriesId(entity.getTimesheetEntriesId());
        dto.setMinutes(entity.getMinutes());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setDate(entity.getDate());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntryMinutesRequestDTO dto, TimesheetEntryMinutes entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setTimesheetEntriesId(dto.getTimesheetEntriesId());
        entity.setMinutes(dto.getMinutes());
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setDate(dto.getDate());
    }
}

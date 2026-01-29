package org.oswfm.timesheetservice.model.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesNormalizedResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntriesNormalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesNormalizedMapper {

    @Autowired
    private WorkforceCodesMapper workforceCodesMapper;

    @Autowired
    private TimesheetEntryMinutesMapper entryMinutesMapper;

    public TimesheetEntriesNormalized toEntity(TimesheetEntriesNormalizedRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        TimesheetEntriesNormalized entity = new TimesheetEntriesNormalized();
        entity.setTimesheetNormalizedId(dto.getTimesheetNormalizedId());
        return entity;
    }

    public TimesheetEntriesNormalizedResponseDTO toResponseDTO(TimesheetEntriesNormalized entity) {
        if (entity == null) {
            return null;
        }

        TimesheetEntriesNormalizedResponseDTO dto = new TimesheetEntriesNormalizedResponseDTO();
        dto.setTimesheetEntriesId(entity.getTimesheetEntriesId());
        dto.setTimesheetNormalizedId(entity.getTimesheetNormalizedId());

        if (entity.getWorkforceCodes() != null) {
            dto.setWorkforceCodes(entity.getWorkforceCodes().stream()
                    .map(workforceCodesMapper::toResponseDTO)
                    .collect(Collectors.toSet()));
        } else {
            dto.setWorkforceCodes(Collections.emptySet());
        }

        if (entity.getEntryMinutes() != null) {
            dto.setEntryMinutes(entity.getEntryMinutes().stream()
                    .map(entryMinutesMapper::toResponseDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setEntryMinutes(Collections.emptyList());
        }

        return dto;
    }

    public TimesheetEntriesNormalizedResponseDTO toResponseDTOWithoutDetails(TimesheetEntriesNormalized entity) {
        if (entity == null) {
            return null;
        }

        TimesheetEntriesNormalizedResponseDTO dto = new TimesheetEntriesNormalizedResponseDTO();
        dto.setTimesheetEntriesId(entity.getTimesheetEntriesId());
        dto.setTimesheetNormalizedId(entity.getTimesheetNormalizedId());
        dto.setWorkforceCodes(Collections.emptySet());
        dto.setEntryMinutes(Collections.emptyList());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesNormalizedRequestDTO dto, TimesheetEntriesNormalized entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setTimesheetNormalizedId(dto.getTimesheetNormalizedId());
    }
}

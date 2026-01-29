package org.oswfm.timesheetservice.model.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNormalizedResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetNormalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimesheetNormalizedMapper {

    @Autowired
    private TimesheetEntriesNormalizedMapper entriesMapper;

    public TimesheetNormalized toEntity(TimesheetNormalizedRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        TimesheetNormalized entity = new TimesheetNormalized();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTimesheetTypeId(dto.getTimesheetTypeId());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public TimesheetNormalizedResponseDTO toResponseDTO(TimesheetNormalized entity) {
        if (entity == null) {
            return null;
        }

        TimesheetNormalizedResponseDTO dto = new TimesheetNormalizedResponseDTO();
        dto.setTimesheetNormalizedId(entity.getTimesheetNormalizedId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setTimesheetTypeId(entity.getTimesheetTypeId());
        dto.setStatus(entity.getStatus());

        if (entity.getTimesheetEntries() != null) {
            dto.setTimesheetEntries(entity.getTimesheetEntries().stream()
                    .map(entriesMapper::toResponseDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setTimesheetEntries(Collections.emptyList());
        }

        return dto;
    }

    public TimesheetNormalizedResponseDTO toResponseDTOWithoutEntries(TimesheetNormalized entity) {
        if (entity == null) {
            return null;
        }

        TimesheetNormalizedResponseDTO dto = new TimesheetNormalizedResponseDTO();
        dto.setTimesheetNormalizedId(entity.getTimesheetNormalizedId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setTimesheetTypeId(entity.getTimesheetTypeId());
        dto.setStatus(entity.getStatus());
        dto.setTimesheetEntries(Collections.emptyList());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetNormalizedRequestDTO dto, TimesheetNormalized entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTimesheetTypeId(dto.getTimesheetTypeId());
        entity.setStatus(dto.getStatus());
    }
}

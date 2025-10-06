package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetSummaryArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetSummaryArchiveMapper {

    public TimesheetSummaryArchive toEntity(TimesheetSummaryArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetSummaryArchive entity = new TimesheetSummaryArchive();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTotalHours(dto.getTotalHours());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public TimesheetSummaryArchiveResponseDTO toResponseDTO(TimesheetSummaryArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetSummaryArchiveResponseDTO dto = new TimesheetSummaryArchiveResponseDTO();
        dto.setTimesheetSummaryId(entity.getTimesheetSummaryId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setTotalHours(entity.getTotalHours());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetSummaryArchiveRequestDTO dto, TimesheetSummaryArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTotalHours(dto.getTotalHours());
        entity.setStatus(dto.getStatus());
    }
}

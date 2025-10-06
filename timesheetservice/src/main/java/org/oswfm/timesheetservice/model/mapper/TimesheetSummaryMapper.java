package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetSummary;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetSummaryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetSummaryMapper {

    public TimesheetSummary toEntity(TimesheetSummaryRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetSummary entity = new TimesheetSummary();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTotalHours(dto.getTotalHours());
        entity.setOperationTypeId(dto.getOperationTypeId());
        return entity;
    }

    public TimesheetSummaryResponseDTO toResponseDTO(TimesheetSummary entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetSummaryResponseDTO dto = new TimesheetSummaryResponseDTO();
        dto.setTimesheetSummaryId(entity.getTimesheetSummaryId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setTotalHours(entity.getTotalHours());
        dto.setOperationTypeId(entity.getOperationTypeId());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetSummaryRequestDTO dto, TimesheetSummary entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setTotalHours(dto.getTotalHours());
        entity.setOperationTypeId(dto.getOperationTypeId());
    }
}

package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.Timesheet;
import org.oswfm.timesheetservice.model.dto.TimesheetRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetMapper {

    public Timesheet toEntity(TimesheetRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Timesheet entity = new Timesheet();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        return entity;
    }

    public TimesheetResponseDTO toResponseDTO(Timesheet entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetResponseDTO dto = new TimesheetResponseDTO();
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetRequestDTO dto, Timesheet entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
    }
}

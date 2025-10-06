package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesOld;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesOldResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesOldMapper {

    public TimesheetEntriesOld toEntity(TimesheetEntriesOldRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesOld entity = new TimesheetEntriesOld();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setWorkCodeId(dto.getWorkCodeId());
        entity.setAccountCodeId(dto.getAccountCodeId());
        entity.setSu1Hours(dto.getSu1Hours());
        entity.setM1Hours(dto.getM1Hours());
        entity.setT1Hours(dto.getT1Hours());
        entity.setW1Hours(dto.getW1Hours());
        entity.setTh1Hours(dto.getTh1Hours());
        entity.setF1Hours(dto.getF1Hours());
        entity.setSa1Hours(dto.getSa1Hours());
        entity.setSu2Hours(dto.getSu2Hours());
        entity.setM2Hours(dto.getM2Hours());
        entity.setT2Hours(dto.getT2Hours());
        entity.setW2Hours(dto.getW2Hours());
        entity.setTh2Hours(dto.getTh2Hours());
        entity.setF2Hours(dto.getF2Hours());
        entity.setSa2Hours(dto.getSa2Hours());
        return entity;
    }

    public TimesheetEntriesOldResponseDTO toResponseDTO(TimesheetEntriesOld entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetEntriesOldResponseDTO dto = new TimesheetEntriesOldResponseDTO();
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setWorkCodeId(entity.getWorkCodeId());
        dto.setAccountCodeId(entity.getAccountCodeId());
        dto.setSu1Hours(entity.getSu1Hours());
        dto.setM1Hours(entity.getM1Hours());
        dto.setT1Hours(entity.getT1Hours());
        dto.setW1Hours(entity.getW1Hours());
        dto.setTh1Hours(entity.getTh1Hours());
        dto.setF1Hours(entity.getF1Hours());
        dto.setSa1Hours(entity.getSa1Hours());
        dto.setSu2Hours(entity.getSu2Hours());
        dto.setM2Hours(entity.getM2Hours());
        dto.setT2Hours(entity.getT2Hours());
        dto.setW2Hours(entity.getW2Hours());
        dto.setTh2Hours(entity.getTh2Hours());
        dto.setF2Hours(entity.getF2Hours());
        dto.setSa2Hours(entity.getSa2Hours());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesOldRequestDTO dto, TimesheetEntriesOld entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriodId(dto.getPayPeriodId());
        entity.setWorkCodeId(dto.getWorkCodeId());
        entity.setAccountCodeId(dto.getAccountCodeId());
        entity.setSu1Hours(dto.getSu1Hours());
        entity.setM1Hours(dto.getM1Hours());
        entity.setT1Hours(dto.getT1Hours());
        entity.setW1Hours(dto.getW1Hours());
        entity.setTh1Hours(dto.getTh1Hours());
        entity.setF1Hours(dto.getF1Hours());
        entity.setSa1Hours(dto.getSa1Hours());
        entity.setSu2Hours(dto.getSu2Hours());
        entity.setM2Hours(dto.getM2Hours());
        entity.setT2Hours(dto.getT2Hours());
        entity.setW2Hours(dto.getW2Hours());
        entity.setTh2Hours(dto.getTh2Hours());
        entity.setF2Hours(dto.getF2Hours());
        entity.setSa2Hours(dto.getSa2Hours());
    }
}

package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetEntriesInOut;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetEntriesInOutResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntriesInOutMapper {

    public TimesheetEntriesInOut toEntity(TimesheetEntriesInOutRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetEntriesInOut entity = new TimesheetEntriesInOut();
        entity.setSu1InTime(dto.getSu1InTime());
        entity.setM1InTime(dto.getM1InTime());
        entity.setT1InTime(dto.getT1InTime());
        entity.setW1InTime(dto.getW1InTime());
        entity.setTh1InTime(dto.getTh1InTime());
        entity.setF1InTime(dto.getF1InTime());
        entity.setSa1InTime(dto.getSa1InTime());
        entity.setSu2InTime(dto.getSu2InTime());
        entity.setM2InTime(dto.getM2InTime());
        entity.setT2InTime(dto.getT2InTime());
        entity.setW2InTime(dto.getW2InTime());
        entity.setTh2InTime(dto.getTh2InTime());
        entity.setF2InTime(dto.getF2InTime());
        entity.setSu1OutTime(dto.getSu1OutTime());
        entity.setM1OutTime(dto.getM1OutTime());
        entity.setT1OutTime(dto.getT1OutTime());
        entity.setW1OutTime(dto.getW1OutTime());
        entity.setTh1OutTime(dto.getTh1OutTime());
        entity.setF1OutTime(dto.getF1OutTime());
        entity.setSa1OutTime(dto.getSa1OutTime());
        entity.setSu2OutTime(dto.getSu2OutTime());
        entity.setM2OutTime(dto.getM2OutTime());
        entity.setT2OutTime(dto.getT2OutTime());
        entity.setW2OutTime(dto.getW2OutTime());
        entity.setTh2OutTime(dto.getTh2OutTime());
        entity.setF2OutTime(dto.getF2OutTime());
        entity.setSa2OutTime(dto.getSa2OutTime());
        return entity;
    }

    public TimesheetEntriesInOutResponseDTO toResponseDTO(TimesheetEntriesInOut entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetEntriesInOutResponseDTO dto = new TimesheetEntriesInOutResponseDTO();
        dto.setTimesheeId(entity.getTimesheeId());
        dto.setSu1InTime(entity.getSu1InTime());
        dto.setM1InTime(entity.getM1InTime());
        dto.setT1InTime(entity.getT1InTime());
        dto.setW1InTime(entity.getW1InTime());
        dto.setTh1InTime(entity.getTh1InTime());
        dto.setF1InTime(entity.getF1InTime());
        dto.setSa1InTime(entity.getSa1InTime());
        dto.setSu2InTime(entity.getSu2InTime());
        dto.setM2InTime(entity.getM2InTime());
        dto.setT2InTime(entity.getT2InTime());
        dto.setW2InTime(entity.getW2InTime());
        dto.setTh2InTime(entity.getTh2InTime());
        dto.setF2InTime(entity.getF2InTime());
        dto.setSu1OutTime(entity.getSu1OutTime());
        dto.setM1OutTime(entity.getM1OutTime());
        dto.setT1OutTime(entity.getT1OutTime());
        dto.setW1OutTime(entity.getW1OutTime());
        dto.setTh1OutTime(entity.getTh1OutTime());
        dto.setF1OutTime(entity.getF1OutTime());
        dto.setSa1OutTime(entity.getSa1OutTime());
        dto.setSu2OutTime(entity.getSu2OutTime());
        dto.setM2OutTime(entity.getM2OutTime());
        dto.setT2OutTime(entity.getT2OutTime());
        dto.setW2OutTime(entity.getW2OutTime());
        dto.setTh2OutTime(entity.getTh2OutTime());
        dto.setF2OutTime(entity.getF2OutTime());
        dto.setSa2OutTime(entity.getSa2OutTime());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetEntriesInOutRequestDTO dto, TimesheetEntriesInOut entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setSu1InTime(dto.getSu1InTime());
        entity.setM1InTime(dto.getM1InTime());
        entity.setT1InTime(dto.getT1InTime());
        entity.setW1InTime(dto.getW1InTime());
        entity.setTh1InTime(dto.getTh1InTime());
        entity.setF1InTime(dto.getF1InTime());
        entity.setSa1InTime(dto.getSa1InTime());
        entity.setSu2InTime(dto.getSu2InTime());
        entity.setM2InTime(dto.getM2InTime());
        entity.setT2InTime(dto.getT2InTime());
        entity.setW2InTime(dto.getW2InTime());
        entity.setTh2InTime(dto.getTh2InTime());
        entity.setF2InTime(dto.getF2InTime());
        entity.setSu1OutTime(dto.getSu1OutTime());
        entity.setM1OutTime(dto.getM1OutTime());
        entity.setT1OutTime(dto.getT1OutTime());
        entity.setW1OutTime(dto.getW1OutTime());
        entity.setTh1OutTime(dto.getTh1OutTime());
        entity.setF1OutTime(dto.getF1OutTime());
        entity.setSa1OutTime(dto.getSa1OutTime());
        entity.setSu2OutTime(dto.getSu2OutTime());
        entity.setM2OutTime(dto.getM2OutTime());
        entity.setT2OutTime(dto.getT2OutTime());
        entity.setW2OutTime(dto.getW2OutTime());
        entity.setTh2OutTime(dto.getTh2OutTime());
        entity.setF2OutTime(dto.getF2OutTime());
        entity.setSa2OutTime(dto.getSa2OutTime());
    }
}

package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.PayPeriods;
import org.oswfm.timesheetservice.model.dto.PayPeriodsRequestDTO;
import org.oswfm.timesheetservice.model.dto.PayPeriodsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PayPeriodsMapper {

    public PayPeriods toEntity(PayPeriodsRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        PayPeriods entity = new PayPeriods();
        entity.setPayPeriodTypeId(dto.getPayPeriodTypeId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        return entity;
    }

    public PayPeriodsResponseDTO toResponseDTO(PayPeriods entity) {
        if (entity == null) {
            return null;
        }

        PayPeriodsResponseDTO dto = new PayPeriodsResponseDTO();
        dto.setPayPeriodId(entity.getPayPeriodId());
        dto.setPayPeriodTypeId(entity.getPayPeriodTypeId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public void updateEntityFromDTO(PayPeriodsRequestDTO dto, PayPeriods entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setPayPeriodTypeId(dto.getPayPeriodTypeId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }
}

package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.WorkforceCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkforceCodesResponseDTO;
import org.oswfm.timesheetservice.model.entity.WorkforceCodes;
import org.springframework.stereotype.Component;

@Component
public class WorkforceCodesMapper {

    public WorkforceCodes toEntity(WorkforceCodesRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        WorkforceCodes entity = new WorkforceCodes();
        entity.setCodeTypeId(dto.getCodeTypeId());
        entity.setCodeId(dto.getCodeId());
        entity.setPrefix(dto.getPrefix());
        entity.setSuffix(dto.getSuffix());
        entity.setShortCodeValue(dto.getShortCodeValue());
        entity.setLongCodeValue(dto.getLongCodeValue());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        return entity;
    }

    public WorkforceCodesResponseDTO toResponseDTO(WorkforceCodes entity) {
        if (entity == null) {
            return null;
        }

        WorkforceCodesResponseDTO dto = new WorkforceCodesResponseDTO();
        dto.setId(entity.getId());
        dto.setCodeTypeId(entity.getCodeTypeId());
        dto.setCodeId(entity.getCodeId());
        dto.setPrefix(entity.getPrefix());
        dto.setSuffix(entity.getSuffix());
        dto.setShortCodeValue(entity.getShortCodeValue());
        dto.setLongCodeValue(entity.getLongCodeValue());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        return dto;
    }

    public void updateEntityFromDTO(WorkforceCodesRequestDTO dto, WorkforceCodes entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setCodeTypeId(dto.getCodeTypeId());
        entity.setCodeId(dto.getCodeId());
        entity.setPrefix(dto.getPrefix());
        entity.setSuffix(dto.getSuffix());
        entity.setShortCodeValue(dto.getShortCodeValue());
        entity.setLongCodeValue(dto.getLongCodeValue());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
    }
}

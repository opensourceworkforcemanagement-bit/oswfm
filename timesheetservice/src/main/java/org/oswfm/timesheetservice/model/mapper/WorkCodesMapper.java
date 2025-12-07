package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.WorkCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkCodesResponseDTO;
import org.oswfm.timesheetservice.model.entity.WorkCodes;
import org.springframework.stereotype.Component;

@Component
public class WorkCodesMapper {

    public WorkCodes toEntity(WorkCodesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        WorkCodes entity = new WorkCodes();
        entity.setShort_work_code(dto.getShort_work_code());
        entity.setLong_work_code(dto.getLong_work_code());
        entity.setPrefix(dto.getPrefix());
        entity.setSuffix(dto.getSuffix());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public WorkCodesResponseDTO toResponseDTO(WorkCodes entity) {
        if (entity == null) {
            return null;
        }
        
        WorkCodesResponseDTO dto = new WorkCodesResponseDTO();
        dto.setWorkCodeId(entity.getWorkCodeId());
        dto.setShort_work_code(entity.getShort_work_code());
        dto.setLong_work_code(entity.getLong_work_code());
        dto.setPrefix(entity.getPrefix());
        dto.setSuffix(entity.getSuffix());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(WorkCodesRequestDTO dto, WorkCodes entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setShort_work_code(dto.getShort_work_code());
        entity.setLong_work_code(dto.getLong_work_code());
        entity.setPrefix(dto.getPrefix());
        entity.setSuffix(dto.getSuffix());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

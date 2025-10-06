package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.WorkCodes;
import org.oswfm.timesheetservice.model.dto.WorkCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.WorkCodesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class WorkCodesMapper {

    public WorkCodes toEntity(WorkCodesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        WorkCodes entity = new WorkCodes();
        entity.setWorkCode(dto.getWorkCode());
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
        dto.setWorkCode(entity.getWorkCode());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(WorkCodesRequestDTO dto, WorkCodes entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setWorkCode(dto.getWorkCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

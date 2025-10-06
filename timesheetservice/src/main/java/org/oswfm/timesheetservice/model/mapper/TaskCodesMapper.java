package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TaskCodes;
import org.oswfm.timesheetservice.model.dto.TaskCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TaskCodesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskCodesMapper {

    public TaskCodes toEntity(TaskCodesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TaskCodes entity = new TaskCodes();
        entity.setTaskCode(dto.getTaskCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public TaskCodesResponseDTO toResponseDTO(TaskCodes entity) {
        if (entity == null) {
            return null;
        }
        
        TaskCodesResponseDTO dto = new TaskCodesResponseDTO();
        dto.setTaskCodeId(entity.getTaskCodeId());
        dto.setTaskCode(entity.getTaskCode());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(TaskCodesRequestDTO dto, TaskCodes entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTaskCode(dto.getTaskCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

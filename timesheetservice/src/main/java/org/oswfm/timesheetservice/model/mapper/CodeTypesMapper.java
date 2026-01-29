package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.CodeTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.CodeTypesResponseDTO;
import org.oswfm.timesheetservice.model.entity.CodeTypes;
import org.springframework.stereotype.Component;

@Component
public class CodeTypesMapper {

    public CodeTypes toEntity(CodeTypesRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        CodeTypes entity = new CodeTypes();
        entity.setCodeTypeName(dto.getCodeTypeName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public CodeTypesResponseDTO toResponseDTO(CodeTypes entity) {
        if (entity == null) {
            return null;
        }

        CodeTypesResponseDTO dto = new CodeTypesResponseDTO();
        dto.setCodeTypeId(entity.getCodeTypeId());
        dto.setCodeTypeName(entity.getCodeTypeName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public void updateEntityFromDTO(CodeTypesRequestDTO dto, CodeTypes entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setCodeTypeName(dto.getCodeTypeName());
        entity.setDescription(dto.getDescription());
    }
}

package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetOperationsTypes;
import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetOperationsTypesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetOperationsTypesMapper {

    public TimesheetOperationsTypes toEntity(TimesheetOperationsTypesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetOperationsTypes entity = new TimesheetOperationsTypes();
        entity.setOperationTypeName(dto.getOperationTypeName());
        entity.setOperationDescription(dto.getOperationDescription());
        return entity;
    }

    public TimesheetOperationsTypesResponseDTO toResponseDTO(TimesheetOperationsTypes entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetOperationsTypesResponseDTO dto = new TimesheetOperationsTypesResponseDTO();
        dto.setOperationTypeId(entity.getOperationTypeId());
        dto.setOperationTypeName(entity.getOperationTypeName());
        dto.setOperationDescription(entity.getOperationDescription());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetOperationsTypesRequestDTO dto, TimesheetOperationsTypes entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setOperationTypeName(dto.getOperationTypeName());
        entity.setOperationDescription(dto.getOperationDescription());
    }
}

package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesSettings;
import org.oswfm.employeeservice.model.dto.EmployeesSettingsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesSettingsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesSettingsMapper {

    public EmployeesSettings toEntity(EmployeesSettingsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesSettings entity = new EmployeesSettings();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setSettingKey(dto.getSettingKey());
        entity.setSettingDescription(dto.getSettingDescription());
        return entity;
    }

    public EmployeesSettingsResponseDTO toResponseDTO(EmployeesSettings entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesSettingsResponseDTO dto = new EmployeesSettingsResponseDTO();
        dto.setEmployeeSettingId(entity.getEmployeeSettingId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setSettingKey(entity.getSettingKey());
        dto.setSettingDescription(entity.getSettingDescription());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesSettingsRequestDTO dto, EmployeesSettings entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setSettingKey(dto.getSettingKey());
        entity.setSettingDescription(dto.getSettingDescription());
    }
}

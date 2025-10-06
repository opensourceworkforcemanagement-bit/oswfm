package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesPreferences;
import org.oswfm.employeeservice.model.dto.EmployeesPreferencesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesPreferencesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesPreferencesMapper {

    public EmployeesPreferences toEntity(EmployeesPreferencesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesPreferences entity = new EmployeesPreferences();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPreferenceKey(dto.getPreferenceKey());
        entity.setPreferenceDescription(dto.getPreferenceDescription());
        return entity;
    }

    public EmployeesPreferencesResponseDTO toResponseDTO(EmployeesPreferences entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesPreferencesResponseDTO dto = new EmployeesPreferencesResponseDTO();
        dto.setEmployeePreferenceId(entity.getEmployeePreferenceId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPreferenceKey(entity.getPreferenceKey());
        dto.setPreferenceDescription(entity.getPreferenceDescription());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesPreferencesRequestDTO dto, EmployeesPreferences entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPreferenceKey(dto.getPreferenceKey());
        entity.setPreferenceDescription(dto.getPreferenceDescription());
    }
}

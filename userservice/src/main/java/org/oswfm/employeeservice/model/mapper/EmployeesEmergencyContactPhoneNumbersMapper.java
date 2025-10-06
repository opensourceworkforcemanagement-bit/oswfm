package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactPhoneNumbers;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactPhoneNumbersResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesEmergencyContactPhoneNumbersMapper {

    public EmployeesEmergencyContactPhoneNumbers toEntity(EmployeesEmergencyContactPhoneNumbersRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesEmergencyContactPhoneNumbers entity = new EmployeesEmergencyContactPhoneNumbers();
        entity.setEmergencyContactId(dto.getEmergencyContactId());
        entity.setPhoneNumberId(dto.getPhoneNumberId());
        return entity;
    }

    public EmployeesEmergencyContactPhoneNumbersResponseDTO toResponseDTO(EmployeesEmergencyContactPhoneNumbers entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesEmergencyContactPhoneNumbersResponseDTO dto = new EmployeesEmergencyContactPhoneNumbersResponseDTO();
        dto.setEmergencyContactInfoId(entity.getEmergencyContactInfoId());
        dto.setEmergencyContactId(entity.getEmergencyContactId());
        dto.setPhoneNumberId(entity.getPhoneNumberId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesEmergencyContactPhoneNumbersRequestDTO dto, EmployeesEmergencyContactPhoneNumbers entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmergencyContactId(dto.getEmergencyContactId());
        entity.setPhoneNumberId(dto.getPhoneNumberId());
    }
}

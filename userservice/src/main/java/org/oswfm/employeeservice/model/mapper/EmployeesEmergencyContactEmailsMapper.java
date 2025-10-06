package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContactEmails;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactEmailsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesEmergencyContactEmailsMapper {

    public EmployeesEmergencyContactEmails toEntity(EmployeesEmergencyContactEmailsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesEmergencyContactEmails entity = new EmployeesEmergencyContactEmails();
        entity.setEmergencyContactId(dto.getEmergencyContactId());
        entity.setEmailAddressId(dto.getEmailAddressId());
        return entity;
    }

    public EmployeesEmergencyContactEmailsResponseDTO toResponseDTO(EmployeesEmergencyContactEmails entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesEmergencyContactEmailsResponseDTO dto = new EmployeesEmergencyContactEmailsResponseDTO();
        dto.setEmergencyContactEmailId(entity.getEmergencyContactEmailId());
        dto.setEmergencyContactId(entity.getEmergencyContactId());
        dto.setEmailAddressId(entity.getEmailAddressId());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesEmergencyContactEmailsRequestDTO dto, EmployeesEmergencyContactEmails entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmergencyContactId(dto.getEmergencyContactId());
        entity.setEmailAddressId(dto.getEmailAddressId());
    }
}

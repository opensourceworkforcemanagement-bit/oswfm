package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmployeesEmergencyContacts;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsRequestDTO;
import org.oswfm.employeeservice.model.dto.EmployeesEmergencyContactsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeesEmergencyContactsMapper {

    public EmployeesEmergencyContacts toEntity(EmployeesEmergencyContactsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmployeesEmergencyContacts entity = new EmployeesEmergencyContacts();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setRelationship(dto.getRelationship());
        return entity;
    }

    public EmployeesEmergencyContactsResponseDTO toResponseDTO(EmployeesEmergencyContacts entity) {
        if (entity == null) {
            return null;
        }
        
        EmployeesEmergencyContactsResponseDTO dto = new EmployeesEmergencyContactsResponseDTO();
        dto.setEmergencyContactId(entity.getEmergencyContactId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setRelationship(entity.getRelationship());
        return dto;
    }

    public void updateEntityFromDTO(EmployeesEmergencyContactsRequestDTO dto, EmployeesEmergencyContacts entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setRelationship(dto.getRelationship());
    }
}

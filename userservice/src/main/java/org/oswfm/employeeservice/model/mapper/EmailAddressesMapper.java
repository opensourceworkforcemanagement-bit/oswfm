package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.EmailAddresses;
import org.oswfm.employeeservice.model.dto.EmailAddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.EmailAddressesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressesMapper {

    public EmailAddresses toEntity(EmailAddressesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmailAddresses entity = new EmailAddresses();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setEmail(dto.getEmail());
        entity.setType(dto.getType());
        entity.setIsPrimary(dto.getIsPrimary());
        return entity;
    }

    public EmailAddressesResponseDTO toResponseDTO(EmailAddresses entity) {
        if (entity == null) {
            return null;
        }
        
        EmailAddressesResponseDTO dto = new EmailAddressesResponseDTO();
        dto.setEmailAddressId(entity.getEmailAddressId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmail(entity.getEmail());
        dto.setType(entity.getType());
        dto.setIsPrimary(entity.getIsPrimary());
        return dto;
    }

    public void updateEntityFromDTO(EmailAddressesRequestDTO dto, EmailAddresses entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setEmail(dto.getEmail());
        entity.setType(dto.getType());
        entity.setIsPrimary(dto.getIsPrimary());
    }
}

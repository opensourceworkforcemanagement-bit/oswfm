package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.PhoneNumbers;
import org.oswfm.employeeservice.model.dto.PhoneNumbersRequestDTO;
import org.oswfm.employeeservice.model.dto.PhoneNumbersResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumbersMapper {

    public PhoneNumbers toEntity(PhoneNumbersRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PhoneNumbers entity = new PhoneNumbers();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setType(dto.getType());
        entity.setIsPrimary(dto.getIsPrimary());
        return entity;
    }

    public PhoneNumbersResponseDTO toResponseDTO(PhoneNumbers entity) {
        if (entity == null) {
            return null;
        }
        
        PhoneNumbersResponseDTO dto = new PhoneNumbersResponseDTO();
        dto.setPhoneNumberId(entity.getPhoneNumberId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setType(entity.getType());
        dto.setIsPrimary(entity.getIsPrimary());
        return dto;
    }

    public void updateEntityFromDTO(PhoneNumbersRequestDTO dto, PhoneNumbers entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setType(dto.getType());
        entity.setIsPrimary(dto.getIsPrimary());
    }
}

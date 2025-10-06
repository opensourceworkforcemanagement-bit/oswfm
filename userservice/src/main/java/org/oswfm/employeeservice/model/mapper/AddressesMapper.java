package org.oswfm.employeeservice.model.mapper;

import org.oswfm.employeeservice.model.entity.Addresses;
import org.oswfm.employeeservice.model.dto.AddressesRequestDTO;
import org.oswfm.employeeservice.model.dto.AddressesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressesMapper {

    public Addresses toEntity(AddressesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Addresses entity = new Addresses();
        entity.setAddressLine1(dto.getAddressLine1());
        entity.setAddressLine2(dto.getAddressLine2());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setCountry(dto.getCountry());
        entity.setIsPrimary(dto.getIsPrimary());
        return entity;
    }

    public AddressesResponseDTO toResponseDTO(Addresses entity) {
        if (entity == null) {
            return null;
        }
        
        AddressesResponseDTO dto = new AddressesResponseDTO();
        dto.setAddressId(entity.getAddressId());
        dto.setAddressLine1(entity.getAddressLine1());
        dto.setAddressLine2(entity.getAddressLine2());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZipCode(entity.getZipCode());
        dto.setCountry(entity.getCountry());
        dto.setIsPrimary(entity.getIsPrimary());
        return dto;
    }

    public void updateEntityFromDTO(AddressesRequestDTO dto, Addresses entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setAddressLine1(dto.getAddressLine1());
        entity.setAddressLine2(dto.getAddressLine2());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setCountry(dto.getCountry());
        entity.setIsPrimary(dto.getIsPrimary());
    }
}

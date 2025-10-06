package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.AccountCodes;
import org.oswfm.timesheetservice.model.dto.AccountCodesRequestDTO;
import org.oswfm.timesheetservice.model.dto.AccountCodesResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountCodesMapper {

    public AccountCodes toEntity(AccountCodesRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        AccountCodes entity = new AccountCodes();
        entity.setAccountCode(dto.getAccountCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public AccountCodesResponseDTO toResponseDTO(AccountCodes entity) {
        if (entity == null) {
            return null;
        }
        
        AccountCodesResponseDTO dto = new AccountCodesResponseDTO();
        dto.setAccountCodeId(entity.getAccountCodeId());
        dto.setAccountCode(entity.getAccountCode());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(AccountCodesRequestDTO dto, AccountCodes entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setAccountCode(dto.getAccountCode());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }
}

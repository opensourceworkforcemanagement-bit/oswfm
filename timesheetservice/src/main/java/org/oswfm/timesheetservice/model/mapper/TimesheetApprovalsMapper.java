package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetApprovals;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetApprovalsMapper {

    public TimesheetApprovals toEntity(TimesheetApprovalsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetApprovals entity = new TimesheetApprovals();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setApproverId(dto.getApproverId());
        entity.setOperationTypeId(dto.getOperationTypeId());
        entity.setApprovalDate(dto.getApprovalDate());
        entity.setComments(dto.getComments());
        return entity;
    }

    public TimesheetApprovalsResponseDTO toResponseDTO(TimesheetApprovals entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetApprovalsResponseDTO dto = new TimesheetApprovalsResponseDTO();
        dto.setTimesheetApprovalId(entity.getTimesheetApprovalId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setApproverId(entity.getApproverId());
        dto.setOperationTypeId(entity.getOperationTypeId());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetApprovalsRequestDTO dto, TimesheetApprovals entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setApproverId(dto.getApproverId());
        entity.setOperationTypeId(dto.getOperationTypeId());
        entity.setApprovalDate(dto.getApprovalDate());
        entity.setComments(dto.getComments());
    }
}

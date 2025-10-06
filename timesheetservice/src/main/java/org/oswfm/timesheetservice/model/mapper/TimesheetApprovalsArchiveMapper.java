package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetApprovalsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetApprovalsArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetApprovalsArchiveMapper {

    public TimesheetApprovalsArchive toEntity(TimesheetApprovalsArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetApprovalsArchive entity = new TimesheetApprovalsArchive();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setApproverId(dto.getApproverId());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setApprovalDate(dto.getApprovalDate());
        entity.setComments(dto.getComments());
        return entity;
    }

    public TimesheetApprovalsArchiveResponseDTO toResponseDTO(TimesheetApprovalsArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetApprovalsArchiveResponseDTO dto = new TimesheetApprovalsArchiveResponseDTO();
        dto.setTimesheetApprovalId(entity.getTimesheetApprovalId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setApproverId(entity.getApproverId());
        dto.setApprovalStatus(entity.getApprovalStatus());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setComments(entity.getComments());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetApprovalsArchiveRequestDTO dto, TimesheetApprovalsArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setApproverId(dto.getApproverId());
        entity.setApprovalStatus(dto.getApprovalStatus());
        entity.setApprovalDate(dto.getApprovalDate());
        entity.setComments(dto.getComments());
    }
}

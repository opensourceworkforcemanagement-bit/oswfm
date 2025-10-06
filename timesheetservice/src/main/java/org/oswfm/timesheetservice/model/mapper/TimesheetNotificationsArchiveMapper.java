package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetNotificationsArchive;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsArchiveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetNotificationsArchiveMapper {

    public TimesheetNotificationsArchive toEntity(TimesheetNotificationsArchiveRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetNotificationsArchive entity = new TimesheetNotificationsArchive();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRecipientId(dto.getRecipientId());
        entity.setNotificationType(dto.getNotificationType());
        entity.setSentAt(dto.getSentAt());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public TimesheetNotificationsArchiveResponseDTO toResponseDTO(TimesheetNotificationsArchive entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetNotificationsArchiveResponseDTO dto = new TimesheetNotificationsArchiveResponseDTO();
        dto.setTimesheetNotificationId(entity.getTimesheetNotificationId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setRecipientId(entity.getRecipientId());
        dto.setNotificationType(entity.getNotificationType());
        dto.setSentAt(entity.getSentAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetNotificationsArchiveRequestDTO dto, TimesheetNotificationsArchive entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRecipientId(dto.getRecipientId());
        entity.setNotificationType(dto.getNotificationType());
        entity.setSentAt(dto.getSentAt());
        entity.setStatus(dto.getStatus());
    }
}

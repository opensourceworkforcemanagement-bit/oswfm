package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.entity.TimesheetNotifications;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsRequestDTO;
import org.oswfm.timesheetservice.model.dto.TimesheetNotificationsResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TimesheetNotificationsMapper {

    public TimesheetNotifications toEntity(TimesheetNotificationsRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TimesheetNotifications entity = new TimesheetNotifications();
        entity.setTimesheetId(dto.getTimesheetId());
        entity.setRecipientId(dto.getRecipientId());
        entity.setNotificationType(dto.getNotificationType());
        entity.setSentAt(dto.getSentAt());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public TimesheetNotificationsResponseDTO toResponseDTO(TimesheetNotifications entity) {
        if (entity == null) {
            return null;
        }
        
        TimesheetNotificationsResponseDTO dto = new TimesheetNotificationsResponseDTO();
        dto.setTimesheetNotificationId(entity.getTimesheetNotificationId());
        dto.setTimesheetId(entity.getTimesheetId());
        dto.setRecipientId(entity.getRecipientId());
        dto.setNotificationType(entity.getNotificationType());
        dto.setSentAt(entity.getSentAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public void updateEntityFromDTO(TimesheetNotificationsRequestDTO dto, TimesheetNotifications entity) {
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

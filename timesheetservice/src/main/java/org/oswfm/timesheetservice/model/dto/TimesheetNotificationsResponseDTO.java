package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNotificationsResponseDTO {

    private Integer timesheetNotificationId;

    private Integer timesheetId;

    private Integer recipientId;

    private String notificationType;

    private LocalTime sentAt;

    private Integer status;

}

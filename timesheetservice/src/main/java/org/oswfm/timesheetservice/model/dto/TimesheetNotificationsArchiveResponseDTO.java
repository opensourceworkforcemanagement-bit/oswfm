package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNotificationsArchiveResponseDTO {

    private Integer  timesheetNotificationId;

    private Integer  timesheetId;

    private Integer  recipientId;

    private String notificationType;

    private LocalDateTime sentAt;

    private Integer  status;

}

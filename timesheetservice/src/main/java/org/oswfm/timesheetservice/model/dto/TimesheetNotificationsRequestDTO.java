package org.oswfm.timesheetservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetNotificationsRequestDTO {

    @NotNull
    private Integer  timesheetId;

    @NotNull
    private Integer  recipientId;

    @NotNull
    @NotBlank
    private String notificationType;

    private LocalDateTime sentAt;

    private Integer  status;

}

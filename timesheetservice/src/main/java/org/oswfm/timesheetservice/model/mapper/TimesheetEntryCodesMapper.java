package org.oswfm.timesheetservice.model.mapper;

import org.oswfm.timesheetservice.model.dto.TimesheetEntryCodesResponseDTO;
import org.oswfm.timesheetservice.model.entity.TimesheetEntryCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimesheetEntryCodesMapper {

    @Autowired
    private WorkforceCodesMapper workforceCodesMapper;

    public TimesheetEntryCodesResponseDTO toResponseDTO(TimesheetEntryCodes entity) {
        if (entity == null) {
            return null;
        }

        TimesheetEntryCodesResponseDTO dto = new TimesheetEntryCodesResponseDTO();
        dto.setTimesheetEntriesId(entity.getId().getTimesheetEntriesId());
        dto.setWorkforceCodesId(entity.getId().getWorkforceCodesId());

        if (entity.getWorkforceCode() != null) {
            dto.setWorkforceCode(workforceCodesMapper.toResponseDTO(entity.getWorkforceCode()));
        }

        return dto;
    }
}

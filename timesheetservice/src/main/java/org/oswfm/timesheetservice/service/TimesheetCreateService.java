package org.oswfm.timesheetservice.service;

import org.oswfm.timesheetservice.model.timesheet.dto.Timesheet;

/**
 * Service interface named {@link timesheetCreateService} for creating timesheets.
 */
public interface TimesheetCreateService {

    /**
     * Creates a new timesheet based on the provided timesheet creation request.
     *
     * @param TimesheetCreateRequest The request containing data to create the timesheet.
     * @return The created timesheet object.
     */
    Timesheet createtimesheet(final Timesheet timesheet);

}
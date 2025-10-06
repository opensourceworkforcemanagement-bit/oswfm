package org.oswfm.timesheetservice.service.impl;


import org.oswfm.timesheetservice.exception.TimesheetAlreadyExistException;
import org.oswfm.timesheetservice.model.timesheet.dto.Timesheet;
// import org.oswfm.timesheetservice.model.timesheet.mapper.TimesheetCreateRequestTotimesheetEntityMapper;
// import org.oswfm.timesheetservice.model.timesheet.mapper.TimesheetEntityTotimesheetMapper;
//import org.oswfm.timesheetservice.repository.TimesheetRepository;
import org.oswfm.timesheetservice.service.TimesheetCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link TimesheetCreateServiceImpl} for creating timesheets.
 */
@Service
@RequiredArgsConstructor
public class TimesheetCreateServiceImpl implements TimesheetCreateService {

    // private final TimesheetRepository timesheetRepository;

    // private final TimesheetCreateRequestTotimesheetEntityMapper timesheetCreateRequestTotimesheetEntityMapper =
    //         timesheetCreateRequestTotimesheetEntityMapper.initialize();

    // private final TimesheetEntityTotimesheetMapper timesheetEntityTotimesheetMapper = timesheetEntityTotimesheetMapper.initialize();

    /**
     * Creates a new timesheet based on the provided timesheet creation request.
     *
     * @param timesheet The request containing data to create the timesheet.
     * @return The created timesheet object.
     * @throws timesheetAlreadyExistException If a timesheet with the same name already exists.
     */
     @Override
     public Timesheet createtimesheet(final Timesheet timesheet)
     {

        // checkUniquenesstimesheetName(timesheetCreateRequest.getName());

        // final TimesheetEntity timesheetEntityToBeSave = timesheetCreateRequestTotimesheetEntityMapper.mapForSaving(timesheetCreateRequest);

        // TimesheetEntity savedtimesheetEntity = timesheetRepository.save(timesheetEntityToBeSave);

        // return timesheetEntityTotimesheetMapper.map(savedtimesheetEntity);

        return null;

    }

    /**
     * Checks if a timesheet with the given name already exists in the repository.
     *
     * @param timesheetName The name of the timesheet to check.
     * @throws timesheetAlreadyExistException If a timesheet with the same name already exists.
     */
    private void checkUniquenesstimesheetName(final String timesheetName) {
        // if (timesheetRepository.existstimesheetEntityByName(timesheetName)) {
        //     throw new TimesheetAlreadyExistException("There is another timesheet with given name: " + timesheetName);
        // }
    }

}

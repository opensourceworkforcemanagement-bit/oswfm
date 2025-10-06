package org.oswfm.timesheetservice.exception;

import java.io.Serial;

/**
 * Exception class named {@link TimesheetAlreadyExistException} thrown when attempting to create a product that already exists.
 */
public class TimesheetAlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 53457089789182737L;

    private static final String DEFAULT_MESSAGE = """
            Timesheet already exist!
            """;

    /**
     * Constructs a new TimesheetAlreadyExistException with a default message.
     */
    public TimesheetAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new TimesheetAlreadyExistException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public TimesheetAlreadyExistException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}

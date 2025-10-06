package org.oswfm.timesheetservice.exception;

import java.io.Serial;

/**
 * Exception class named {@link TimesheetNotFoundException} thrown when a requested timesheet cannot be found.
 */
public class TimesheetNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5854010258697200749L;

    private static final String DEFAULT_MESSAGE = """
            Timesheet not found!
            """;

    /**
     * Constructs a new TimesheetNotFoundException with a default message.
     */
    public TimesheetNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new TimesheetNotFoundException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public TimesheetNotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}

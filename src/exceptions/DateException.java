package exceptions;

import java.time.DateTimeException;

public class DateException extends Exception {
    public DateException(String message) {
        super(message);
    }
}

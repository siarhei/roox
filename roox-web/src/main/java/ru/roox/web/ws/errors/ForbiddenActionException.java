package ru.roox.web.ws.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Is thrown when an user has not privileges to access a resource
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenActionException extends RuntimeException {
    private static final long serialVersionUID = -9212295200718302384L;

    public ForbiddenActionException() {
        super();
    }

    public ForbiddenActionException(String message) {
        super(message);
    }

    public ForbiddenActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenActionException(Throwable cause) {
        super(cause);
    }
}

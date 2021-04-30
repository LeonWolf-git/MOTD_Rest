package de.lewolf.MOTD.exceptions;

public class URLNotResponsiveException extends RuntimeException {

    public URLNotResponsiveException(String message) {
        super(message);
    }

    public URLNotResponsiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public URLNotResponsiveException(Throwable cause) {
        super(cause);
    }

    public URLNotResponsiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

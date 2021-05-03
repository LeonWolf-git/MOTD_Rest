package de.lewolf.MOTD.exceptions;

public class SomethingWentTerriblyWrongException extends RuntimeException{
    public SomethingWentTerriblyWrongException(String message) {
        super(message);
    }

    public SomethingWentTerriblyWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public SomethingWentTerriblyWrongException(Throwable cause) {
        super(cause);
    }

    public SomethingWentTerriblyWrongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

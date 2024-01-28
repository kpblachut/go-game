package org.example.server.exceptions;

public class InvalidGameTurnEncounteredException extends Exception{
    public InvalidGameTurnEncounteredException() {
    }

    public InvalidGameTurnEncounteredException(String message) {
        super(message);
    }

    public InvalidGameTurnEncounteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGameTurnEncounteredException(Throwable cause) {
        super(cause);
    }

    public InvalidGameTurnEncounteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

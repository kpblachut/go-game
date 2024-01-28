package org.example.server.exceptions;

public class OutOfGameBoardException extends Exception{
    public OutOfGameBoardException() {
    }

    public OutOfGameBoardException(String message) {
        super(message);
    }

    public OutOfGameBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfGameBoardException(Throwable cause) {
        super(cause);
    }

    public OutOfGameBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

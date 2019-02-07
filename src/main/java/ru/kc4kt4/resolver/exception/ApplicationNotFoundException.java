package ru.kc4kt4.resolver.exception;

/**
 * The type Application not found exception.
 */
public class ApplicationNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Application not found exception.
     *
     * @param message the message
     */
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}

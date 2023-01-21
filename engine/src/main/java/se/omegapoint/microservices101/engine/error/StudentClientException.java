package se.omegapoint.microservices101.engine.error;

public class StudentClientException extends RuntimeException {

    public StudentClientException(String message, Throwable e) {
        super(message, e);
    }
}

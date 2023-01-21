package se.omegapoint.microservices101.engine.error;

import org.springframework.http.HttpStatus;

import static java.util.Objects.requireNonNull;

class OPClassRoomError {
    public final HttpStatus status;
    public final String message;

    OPClassRoomError(final HttpStatus status, final String message) {
        this.status = requireNonNull(status);
        this.message = requireNonNull(message);
    }
}

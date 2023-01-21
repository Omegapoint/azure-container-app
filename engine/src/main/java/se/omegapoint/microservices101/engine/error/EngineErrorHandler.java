package se.omegapoint.microservices101.engine.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EngineErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(EngineErrorHandler.class);

    @ExceptionHandler(StudentClientException.class)
    public ResponseEntity<OPClassRoomError> handle(final StudentClientException studentClientException) {

        logger.error("Could not reach student service", studentClientException);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new OPClassRoomError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Could not reach student service. Message: " + studentClientException.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OPClassRoomError> handle(final Exception e) {

        logger.error("Internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new OPClassRoomError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}

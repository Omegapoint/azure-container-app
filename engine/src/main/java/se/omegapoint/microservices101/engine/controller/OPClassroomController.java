package se.omegapoint.microservices101.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import se.omegapoint.microservices101.engine.model.Student;
import se.omegapoint.microservices101.engine.service.OPClassroomEngineService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/")
public class OPClassroomController {
    private final OPClassroomEngineService opClassroomEngineService;
    private final Logger logger = LoggerFactory.getLogger(OPClassroomController.class);

    public OPClassroomController(OPClassroomEngineService opClassroomEngineService) {
        this.opClassroomEngineService = opClassroomEngineService;
    }

    @GetMapping(value = "hello")
    public Mono<ResponseEntity<String>> hello() {
        logger.info("hello from engine");
        return Mono.just(new ResponseEntity<>("Hello from engine", HttpStatus.OK));
    }

    @GetMapping(value = "students")
    public Mono<ResponseEntity<List<Student>>> getAllStudents() {
        logger.info("getAllStudents from engine");
        return opClassroomEngineService
                .getStudents()
                .map(students -> new ResponseEntity<>(students, HttpStatus.OK));
    }
}

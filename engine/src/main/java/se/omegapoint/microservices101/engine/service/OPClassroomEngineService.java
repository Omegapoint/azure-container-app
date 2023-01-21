package se.omegapoint.microservices101.engine.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import se.omegapoint.microservices101.engine.integration.StudentServiceClient;
import se.omegapoint.microservices101.engine.model.Student;

import java.util.List;

@Service
public class OPClassroomEngineService {
    private final StudentServiceClient studentServiceClient;

    public OPClassroomEngineService(final StudentServiceClient studentServiceClient) {
        this.studentServiceClient = studentServiceClient;
    }

    public Mono<List<Student>> getStudents() {
        return studentServiceClient.getStudents();
    }
}

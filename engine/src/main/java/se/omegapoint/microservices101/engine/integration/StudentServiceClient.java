package se.omegapoint.microservices101.engine.integration;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.omegapoint.microservices101.engine.error.StudentClientException;
import se.omegapoint.microservices101.engine.model.Student;

import java.time.Duration;
import java.util.List;

public class StudentServiceClient {
    private final WebClient webClient;

    public StudentServiceClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Student>> getStudents() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("students")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Student>>() {
                })
                .timeout(Duration.ofSeconds(30))
                .onErrorMap(e -> new StudentClientException("An internal server occurred", e));
    }
}

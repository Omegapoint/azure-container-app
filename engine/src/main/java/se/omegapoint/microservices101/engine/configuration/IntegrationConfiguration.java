package se.omegapoint.microservices101.engine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import se.omegapoint.microservices101.engine.integration.StudentServiceClient;

import java.util.Map;

@Configuration
public class IntegrationConfiguration {
    @Bean
    public StudentServiceClient studentServiceClient() {
        final String environmentDefaultDomain = System.getenv("ENVIRONMENT_DEFAULT_DOMAIN");
        if (environmentDefaultDomain == null || "".equals(environmentDefaultDomain)) {
            throw new RuntimeException("ENVIRONMENT_DEFAULT_DOMAIN is not set");
        }
        final String studentBaseUrl = "https://student-service.internal." + environmentDefaultDomain;

        WebClient webClient = WebClient
                .builder()
                .baseUrl(studentBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Map.of("url", studentBaseUrl))
                .build();

        return new StudentServiceClient(webClient);
    }
}

FROM adoptopenjdk/openjdk14:debianslim-slim

VOLUME /tmp
VOLUME /logs

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/student-service-0.0.1.jar

# Change our working directory so that our files will have the correct path
WORKDIR /home/appuser

# Add the application's jar to the container
ADD ${JAR_FILE} student-service.jar

# Set up appuser and run this container as this role
RUN useradd appuser -d /home/appuser && \
    chown -R appuser /home/appuser
USER appuser

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./student-service.jar"]
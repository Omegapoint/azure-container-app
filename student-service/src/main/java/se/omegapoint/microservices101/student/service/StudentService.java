package se.omegapoint.microservices101.student.service;

import org.springframework.stereotype.Service;
import se.omegapoint.microservices101.student.model.Student;

import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents() {
        return List.of(
                new Student("John Doe"),
                new Student("Joanne Doedoe"));
    }
}

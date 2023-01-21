package se.omegapoint.microservices101.student.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.microservices101.student.model.Student;
import se.omegapoint.microservices101.student.service.StudentService;

import java.util.List;

@RestController
public class StudentController {
    public final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/students")
    public List<Student> getStudents() {
        logger.info("getStudents from student-service");
        return studentService.getStudents();
    }
}

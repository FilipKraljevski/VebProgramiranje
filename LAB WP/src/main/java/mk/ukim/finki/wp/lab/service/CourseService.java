package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumaration.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);

    Course addStudentInCourse(String username, Long courseId);

    List<Course> listAll();

    Optional<Course> save(String name, String description, Long teachersId, Long id, LocalDate date, Type type);

    void deleteById(Long id);

    Optional<Course> findById(Long id);
}

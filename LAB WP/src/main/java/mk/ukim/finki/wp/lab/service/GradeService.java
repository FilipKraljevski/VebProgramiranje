package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GradeService {
    List<Grade> findByCourse(Course c);

    Grade findStudentByCourse(Course c, Student s);

    Optional<Grade> findById(Long id);

    void update(Grade g, Character grade, LocalDateTime time);

    void save(Grade g);

    List<Grade> findByTimestampBetween(LocalDateTime from, LocalDateTime to, Course c);
}

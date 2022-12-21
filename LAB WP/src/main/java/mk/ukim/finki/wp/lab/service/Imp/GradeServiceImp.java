package mk.ukim.finki.wp.lab.service.Imp;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImp implements GradeService {
    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImp(GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Grade> findByCourse(Course c) {
        return gradeRepository.findAll().stream()
                .filter(r -> r.getCourse().equals(c))
                .collect(Collectors.toList());
    }


    @Override
    public Grade findStudentByCourse(Course c, Student s) {
        return gradeRepository.findAll().stream()
                .filter(r -> r.getCourse().equals(c) && r.getStudent().equals(s))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(Grade g, Character grade, LocalDateTime time) {
        Grade tmp = gradeRepository.findById(g.getId()).orElse(null);
        tmp.setGrade(grade);
        tmp.setTimestamp(time);
        gradeRepository.save(tmp);
    }

    @Override
    public void save(Grade g) {
        gradeRepository.save(g);
    }

    @Override
    public List<Grade> findByTimestampBetween(LocalDateTime from, LocalDateTime to, Course course){
        List<Grade> g = gradeRepository.findByTimestampBetween(from, to);
        return g.stream()
                .filter(r -> r.getCourse().equals(course))
                .collect(Collectors.toList());
    }
}

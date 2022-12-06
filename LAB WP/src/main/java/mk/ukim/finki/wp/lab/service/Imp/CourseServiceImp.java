package mk.ukim.finki.wp.lab.service.Imp;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public CourseServiceImp(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student s = studentService.listAll().stream()
                .filter(r -> r.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        Course c = courseRepository.findById(courseId)
                .orElse(null);
        return courseRepository.addStudentToCourse(s, c);
    }

    public List<Course> listAll(){
        return courseRepository.findAllCourses();
    }

    @Override
    public Optional<Course> save(String name, String description, Long teachersId, Long id , LocalDate date) {
        return courseRepository.save(name, description, teachersId, id, date);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}

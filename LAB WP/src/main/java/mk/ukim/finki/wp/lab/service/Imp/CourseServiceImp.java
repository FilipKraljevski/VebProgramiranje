package mk.ukim.finki.wp.lab.service.Imp;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumaration.Type;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;

    public CourseServiceImp(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Course c = courseRepository.findById(courseId).orElse(null);
        if(c != null){
            return c.getStudents();
        }
        return new ArrayList<>();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student s = studentRepository.findById(username).orElse(null);
        Course c = courseRepository.findById(courseId)
                .orElse(null);
        if(c != null){
            c.getStudents().removeIf(r -> r.equals(s));
            c.getStudents().add(s);
            return courseRepository.save(c);
        }
        return null;
    }

    public List<Course> listAll(){
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Course> save(String name, String description, Long teachersId, Long id , LocalDate date, Type type) {
        Teacher teacher = teacherRepository.findById(teachersId).orElse(null);
        List<Student> s = listStudentsByCourse(id);
        if(courseRepository.findById(id).isPresent()){
            Course c = courseRepository.findById(id).orElse(null);
            c.setName(name);
            c.setDescription(description);
            c.setTeacher(teacher);
            c.setStudents(s);
            c.setDate(date);
            c.setType(type);
            courseRepository.save(c);
            return Optional.of(c);
        }
        return Optional.of(courseRepository.save(new Course(name, description, s, teacher, date, type)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Course c = courseRepository.findById(id).orElse(null);
        gradeRepository.deleteByCourse(c);
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}

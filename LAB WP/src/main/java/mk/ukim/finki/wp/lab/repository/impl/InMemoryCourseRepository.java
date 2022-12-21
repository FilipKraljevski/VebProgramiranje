package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumaration.Type;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCourseRepository {
    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }

    public Optional<Course> findById(Long courseId){
        return DataHolder.courses.stream()
                .filter(r -> r.getCourseId().equals(courseId))
                .findFirst();
    }

    public List<Student> findAllStudentsByCourse(Long courseId){
        Optional<Course> tmp = DataHolder.courses.stream()
                .filter(r -> r.getCourseId().equals(courseId))
                .findFirst();
        return tmp.map(Course::getStudents).orElse(null);
    }

    public Course addStudentToCourse(Student student, Course course){
        for (Course c : DataHolder.courses) {
            if (course.equals(c)) {
                Student s = c.getStudents().stream()
                        .filter(r -> r.getUsername().equals(student.getUsername()))
                        .findFirst().orElse(null);
                if(s == null) {
                    c.getStudents().add(student);
                }
                return course;
            }
        }
        return null;
    }

    public Optional<Course> save(String name, String description, Long teachersId, Long id, LocalDate date, Type type){
        Course course;
        if(id == 0){
            Teacher teacher = DataHolder.teachers.stream().filter(r -> r.getId().equals(teachersId)).findFirst().orElse(null);
            List<Student> students = new ArrayList<>();
            course = new Course(name, description, students, teacher, date, type);
            DataHolder.courses.add(course);
        }
        else {
            course = DataHolder.courses.stream().filter(r -> r.getCourseId().equals(id)).findFirst().orElse(null);
            DataHolder.courses.removeIf(r -> r.getCourseId().equals(id));
            Teacher teacher = DataHolder.teachers.stream().filter(r -> r.getId().equals(teachersId)).findFirst().orElse(null);
            Course p = new Course(name, description, course.getStudents(), teacher, date, type);
            DataHolder.courses.add(p);
        /*List<Student> students = new ArrayList<>();
        Course course = new Course(name, description, students, teacher);
        DataHolder.courses.add(course);*/
        }
        return Optional.of(course);
    }

    public void deleteById(Long id){
        DataHolder.courses.removeIf(r -> r.getCourseId().equals(id));
    }

}

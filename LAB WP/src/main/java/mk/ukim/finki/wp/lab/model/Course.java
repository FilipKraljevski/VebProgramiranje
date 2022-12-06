package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Course {
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;
    private LocalDate date;
    private String price;

    public Course(String name, String description, List<Student> students, Teacher teacher, LocalDate date) {
        this.courseId = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
        this.date = date;
    }
}

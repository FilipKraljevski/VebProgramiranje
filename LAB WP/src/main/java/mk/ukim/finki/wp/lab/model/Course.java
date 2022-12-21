package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumaration.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String name;

    private String description;

    @ManyToMany
    private List<Student> students;

    @ManyToOne
    private Teacher teacher;

    private LocalDate date;

    //private String price;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Course(String name, String description, List<Student> students, Teacher teacher, LocalDate date, Type type) {
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
        this.date = date;
        this.type = type;
    }

    public Course() {
    }
}

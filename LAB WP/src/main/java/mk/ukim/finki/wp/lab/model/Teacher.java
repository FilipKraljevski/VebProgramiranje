package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = TeacherFullNameConverter.class)
    private TeacherFullName teacherFullname;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher(TeacherFullName teacherFullname, LocalDate dateOfEmployment) {
        this.teacherFullname = teacherFullname;
        this.dateOfEmployment = dateOfEmployment;
    }

    public Teacher() {
    }
}

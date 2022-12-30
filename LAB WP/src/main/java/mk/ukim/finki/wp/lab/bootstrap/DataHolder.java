package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullName;
import mk.ukim.finki.wp.lab.model.enumaration.Role;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public final static List<Student> students = new ArrayList<>();
    public final static List<Course> courses = new ArrayList<>();
    public final static List<Teacher> teachers = new ArrayList<>();

    private final PasswordEncoder passwordEncoder;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;


    public DataHolder(PasswordEncoder passwordEncoder, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    /*@PostConstruct
    public void init(){
        students.add(new Student("FilipKraljevski", "fk", "Filip", "Kraljevski"));
        students.add(new Student("BojanSpasovski", "bs", "Bojan", "Spasovski"));
        students.add(new Student("VladimirDamjanovski", "vd", "Vladimir", "Damjanovski"));
        students.add(new Student("TimiTrajkovski", "tt", "Timi", "Trajkovski"));
        students.add(new Student("ViktorMarkovski", "vm", "Viktor", "Markovski"));
        List<Student> students = new ArrayList<>();
        teachers.add(new Teacher("Dimitar", "Trajanov"));
        teachers.add(new Teacher("Igor", "Mishkovski"));
        teachers.add(new Teacher("Stefan", "Andonov"));
        teachers.add(new Teacher("Sonja", "Filipovska"));
        teachers.add(new Teacher("Mile", "Jovanov"));
        StudentRepository studentRepository = new StudentRepository();
        //studentRepository.findAllByNameOrSurname("Filip")
        LocalDate date = LocalDate.now();
        courses.add(new Course("Veb programiranje", "Treta godina - zimski",
                students, teachers.get(0), date, Type.WINTER));
        courses.add(new Course("Operativni sistemi", "Vtora godina - leten",
                studentRepository.findAllByNameOrSurname("Bojan"), teachers.get(0), date, Type.WINTER));
        courses.add(new Course( "Elektronska i mobilna trgovija", "Treta godina - leten",
                studentRepository.findAllByNameOrSurname("Vladimir"), teachers.get(0), date, Type.WINTER));
        courses.add(new Course( "Kompjuterski mrezi", "Vtora godina - zimski",
                studentRepository.findAllByNameOrSurname("Timi"),teachers.get(0), date, Type.WINTER));
        courses.add(new Course( "Strukturno programiranje", "Prva godina - zimski",
                studentRepository.findAllByNameOrSurname("Viktor"), teachers.get(0), date, Type.WINTER));
    }*/

    @PostConstruct
    public void init(){
        String admin = "admin";
        studentRepository.save(new Student(admin, passwordEncoder.encode(admin), admin, admin, Role.ROLE_ADMIN));
        TeacherFullName teacherFullName = new TeacherFullName();
        teacherFullName.setName("teacher");
        teacherFullName.setSurname("teacher");
        teacherRepository.save(new Teacher(teacherFullName, LocalDate.now()));
    }
}

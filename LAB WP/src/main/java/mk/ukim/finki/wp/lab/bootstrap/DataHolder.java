package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
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

    @PostConstruct
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
                students, teachers.get(0), date));
        courses.add(new Course("Operativni sistemi", "Vtora godina - leten",
                studentRepository.findAllByNameOrSurname("Bojan"), teachers.get(0), date));
        courses.add(new Course( "Elektronska i mobilna trgovija", "Treta godina - zimski",
                studentRepository.findAllByNameOrSurname("Vladimir"), teachers.get(0), date));
        courses.add(new Course( "Kompjuterski mrezi", "Vtora godina - leten",
                studentRepository.findAllByNameOrSurname("Timi"),teachers.get(0), date));
        courses.add(new Course( "Strukturno programiranje", "Prva godina - zimski",
                studentRepository.findAllByNameOrSurname("Viktor"), teachers.get(0), date));
    }
}

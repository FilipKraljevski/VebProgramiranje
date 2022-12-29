package mk.ukim.finki.wp.lab.service.Imp;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumaration.Role;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll().stream()
                .filter(r -> r.getRole().equals(Role.ROLE_STUDENT))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> searchByNameOrSurname(String name, String surname) {
        return studentRepository.findAllByNameOrSurname(name, surname);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        Student s = new Student(username, password, name, surname, Role.ROLE_STUDENT);
        Student tmp = studentRepository.findAll().stream()
                .filter(r -> r.getUsername().equals(username))
                .findFirst().orElse(null);
        if(tmp == null){
            studentRepository.save(s);
        }
        return s;
    }

    @Override
    public Optional<Student> findById(String username) {
        return studentRepository.findById(username);
    }

    @Override
    public Optional<Student> login(String username, String password) {
        if(studentRepository.findByUsernameAndPassword(username, password).isEmpty()){
            throw new InvalidUserCredentialsException();
        }
        else {
            return studentRepository.findByUsernameAndPassword(username, password);
        }
    }
}

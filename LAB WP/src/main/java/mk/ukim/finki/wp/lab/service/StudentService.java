package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface StudentService extends UserDetailsService {
    List<Student> listAll();

    List<Student> searchByNameOrSurname(String name, String surname);

    Student save(String username, String password, String name, String surname);

    Optional<Student> findById(String username);

    Optional<Student> login(String username, String password);
}

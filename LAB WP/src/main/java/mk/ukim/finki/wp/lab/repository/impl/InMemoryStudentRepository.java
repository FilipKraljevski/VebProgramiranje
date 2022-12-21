package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    public List<Student> findAllStudents(){
        return DataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text){
        return DataHolder.students.stream()
                .filter(r -> r.getName().equals(text) || r.getSurname().equals(text))
                .collect(Collectors.toList());
    }
}

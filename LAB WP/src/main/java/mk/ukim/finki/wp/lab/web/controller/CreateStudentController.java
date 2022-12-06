package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/createStudent")
public class CreateStudentController {
    private final StudentService studentService;

    public CreateStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getCreateStudentPages(){
        return "createStudent";
    }

    @PostMapping
    public String create(@RequestParam String username, @RequestParam String password, @RequestParam String name,
                         @RequestParam String surname){
        studentService.save(username, password, name, surname);
        return "redirect:/addStudent";
    }
}

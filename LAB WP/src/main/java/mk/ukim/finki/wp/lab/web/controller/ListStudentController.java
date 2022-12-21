package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/addStudent")
public class ListStudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;

    public ListStudentController(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public String getAddStudentPage(Model model){
        model.addAttribute("students", studentService.listAll());
        return "listStudents";
    }

    @PostMapping
    public String add(@RequestParam String size, HttpServletRequest req, Model model){
        Object o = req.getSession().getAttribute("id");
        Long id = Long.valueOf(String.valueOf(o));
        courseService.addStudentInCourse(size, id);
        Student s= studentService.findById(size).orElse(null);
        Course c = courseService.findById(id).orElse(null);
        if(gradeService.findStudentByCourse(c, s) == null){
            Grade g = new Grade('F', s, c, LocalDateTime.now());
            gradeService.save(g);
        }
        return "redirect:/studentEnrollmentSummary";
    }
}

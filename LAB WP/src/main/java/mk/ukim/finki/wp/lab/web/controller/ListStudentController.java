package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/addStudent")
public class ListStudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    public ListStudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
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
        return "redirect:/studentEnrollmentSummary";
    }
}

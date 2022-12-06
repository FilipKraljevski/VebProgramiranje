package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/studentEnrollmentSummary")
public class StudentEnrollmentSummaryController {
    private final CourseService courseService;

    public StudentEnrollmentSummaryController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getStudentEnrollmentSummary(HttpServletRequest req, Model model){
        Object o = req.getSession().getAttribute("id");
        Long id = Long.valueOf(String.valueOf(o));
        Course courses = courseService.findById(id).orElse(null);
        model.addAttribute("students", courseService.listStudentsByCourse(id));
        model.addAttribute("course", courses);
        return "studentsInCourse";
    }
}

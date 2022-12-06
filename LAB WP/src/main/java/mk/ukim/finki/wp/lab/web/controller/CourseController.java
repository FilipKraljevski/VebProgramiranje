package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Comparator;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model, HttpServletRequest req){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(10);
        courseService.listAll().sort(Comparator.comparing(Course::getName));
        model.addAttribute("ses", session.getServletContext().getAttribute("num"));
        model.addAttribute("courses", courseService.listAll());
        return "listCourses";
    }

    @PostMapping
    public String listStudents(@RequestParam Long courseId, Model model, HttpServletRequest req){
        req.getSession().setAttribute("id", courseId);
        model.addAttribute("courseId", courseId);
        return "redirect:/addStudent";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam String name, @RequestParam String description, @RequestParam Long teacherId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             HttpServletRequest req){
        Object o = req.getSession().getAttribute("courseId");
        Long id = Long.valueOf(String.valueOf(o));
        courseService.save(name, description, teacherId, id, date);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        courseService.deleteById(id);
        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model, HttpServletRequest req){
        if(courseService.findById(id).isPresent()) {
            Course courses = courseService.findById(id).orElse(null);
            model.addAttribute("course",courses);
            model.addAttribute("teachers", teacherService.findAll());
            req.getSession().setAttribute("courseId", id);
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model, HttpServletRequest req){
        model.addAttribute("teachers", teacherService.findAll());
        req.getSession().setAttribute("courseId", 0);
        return "add-course";
    }
}

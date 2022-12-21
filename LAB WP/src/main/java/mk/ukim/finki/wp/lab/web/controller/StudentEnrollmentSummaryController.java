package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/studentEnrollmentSummary")
public class StudentEnrollmentSummaryController {
    private final CourseService courseService;
    private final GradeService gradeService;

    public StudentEnrollmentSummaryController(CourseService courseService, GradeService gradeService) {
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public String getStudentEnrollmentSummary(HttpServletRequest req, Model model){
        Object o = req.getSession().getAttribute("id");
        Long id = Long.valueOf(String.valueOf(o));
        Course course = courseService.findById(id).orElse(null);
        //List<Student> s = courseService.listStudentsByCourse(id);
        List<Grade> g =  gradeService.findByCourse(course);
        /*Collections.sort(s, new SortStudentsBySurname());
        Collections.sort(g, new SortGradesByStudentSurname());
        model.addAttribute("students", s);*/
        model.addAttribute("course", course);
        model.addAttribute("grades", g);
        return "studentsInCourse";
    }

    @PostMapping("/addGrade")
    public String addOrEditGrade(@RequestParam Long gradeId, HttpServletRequest req, Model model){
        Grade g =  gradeService.findById(gradeId).orElse(null);
        req.getSession().setAttribute("gradeId", gradeId);
        model.addAttribute("grade", g);
        return "add-grade";
    }

    @PostMapping("/putGrade")
    public String save(@RequestParam Character grade, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                       LocalDateTime time, HttpServletRequest req){
        Object o = req.getSession().getAttribute("gradeId");
        Long id = Long.valueOf(String.valueOf(o));
        Grade g =  gradeService.findById(id).orElse(null);
        gradeService.update(g, grade, time);
        return "redirect:/studentEnrollmentSummary";
    }

    @PostMapping("/filterGrade")
    public String filterGradeByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeFrom,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeTo,
                                    HttpServletRequest req, Model model){
        Object o = req.getSession().getAttribute("id");
        Long id = Long.valueOf(String.valueOf(o));
        Course course = courseService.findById(id).orElse(null);
        List<Grade> g = gradeService.findByTimestampBetween(timeFrom, timeTo, course);
        model.addAttribute("course", course);
        model.addAttribute("grades", g);
        return "studentsInCourse";

    }

    static class SortStudentsBySurname implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            return o1.getSurname().compareTo(o2.getSurname());
        }
    }

    static class SortGradesByStudentSurname implements Comparator<Grade>{

        @Override
        public int compare(Grade o1, Grade o2) {
            return o1.getStudent().getSurname().compareTo(o2.getStudent().getSurname());
        }
    }

}

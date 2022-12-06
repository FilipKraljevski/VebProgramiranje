package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/servlet/studentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public StudentEnrollmentSummary(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Object o = req.getSession().getAttribute("id");
        Long id = Long.valueOf(String.valueOf(o));
        context.setVariable("students", courseService.listStudentsByCourse(id));
        Course c = courseService.listAll().stream()
                .filter(r -> r.getCourseId().equals(id))
                .findFirst().orElse(null);
        context.setVariable("course", c);
        springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }
}

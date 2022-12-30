package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullName;
import mk.ukim.finki.wp.lab.model.enumaration.Type;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private HtmlUnitDriver driver;
    private boolean dataInitialized = false;
    private Teacher t;
    private String desc = "description";
    private LocalDate date = LocalDate.now();

    @BeforeEach
    public void setup(){
        driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy(){
        if(driver != null){
            driver.close();
        }
    }

    private void initData(){
        if (!dataInitialized) {
            TeacherFullName teacherFullName = new TeacherFullName();
            teacherFullName.setName("teacher");
            teacherFullName.setSurname("teacher");
            t = new Teacher(teacherFullName, LocalDate.now());
            teacherRepository.save(t);
            dataInitialized = true;
            courseRepository.save(new Course("test", desc, null, t, date, Type.WINTER));
        }
    }

    @Test
    public void testScenario(){
        CoursesPage coursesPage = CoursesPage.openCourses(driver);
        coursesPage.AssertElements(1, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(driver);
        coursesPage = LoginPage.login(driver, loginPage, "admin", "a");
        coursesPage.AssertElements(1, 1, 1, 1);
        coursesPage = AddCoursePage.addProduct(driver, "c1", desc, "teacher", date.toString(),
                Type.WINTER.toString());
        coursesPage.AssertElements(2, 2, 2, 1);
        coursesPage = AddCoursePage.addProduct(driver, "c2", desc, "teacher", date.toString(),
                Type.WINTER.toString());
        coursesPage.AssertElements(3, 3, 3, 1);
    }
}

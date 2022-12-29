package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CoursesPage extends AbstractPage{
    @FindBy(css = "div[class=course]")
    List<WebElement> courseRows;

    @FindBy(css = ".delete-course")
    List<WebElement> deleteCourse;

    @FindBy(css = ".edit-course")
    List<WebElement> editCourse;

    @FindBy(css = ".add-course")
    List<WebElement> addCourse;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage openCourses(WebDriver driver){
        get(driver, "/courses");
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public void AssertElements(int coursesNumber, int deleteButtons, int editButtons, int addButtons){
        Assert.assertEquals("rows do not match", coursesNumber, courseRows.size());
        Assert.assertEquals("delete do not match", deleteButtons, deleteCourse.size());
        Assert.assertEquals("edit do not match", editButtons, editCourse.size());
        Assert.assertEquals("add is visible", addButtons, addCourse.size());
    }
}

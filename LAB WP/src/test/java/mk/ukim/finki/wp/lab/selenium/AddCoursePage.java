package mk.ukim.finki.wp.lab.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddCoursePage extends AbstractPage{
    private WebElement name;
    private WebElement description;
    private WebElement teacherId;
    private WebElement date;
    private WebElement type;
    private WebElement submit;

    public AddCoursePage(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage addProduct(WebDriver driver, String name, String description, String teacherId, String date,
                                         String type) {
        get(driver, "/courses/add-form");
        AddCoursePage addCoursePage = PageFactory.initElements(driver, AddCoursePage.class);
        addCoursePage.name.sendKeys(name);
        addCoursePage.description.sendKeys(description);
        addCoursePage.teacherId.sendKeys(teacherId);
        addCoursePage.teacherId.click();
        addCoursePage.teacherId.findElement(By.xpath("//option[. = '" + teacherId + "']")).click();
        addCoursePage.date.click();
        addCoursePage.date.sendKeys(date);
        addCoursePage.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }
}

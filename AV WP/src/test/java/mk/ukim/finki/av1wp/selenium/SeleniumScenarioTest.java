package mk.ukim.finki.av1wp.selenium;

import mk.ukim.finki.av1wp.model.Category;
import mk.ukim.finki.av1wp.model.Manufacturer;
import mk.ukim.finki.av1wp.model.Role;
import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.service.CategoryService;
import mk.ukim.finki.av1wp.service.ManufacturerService;
import mk.ukim.finki.av1wp.service.ProductService;
import mk.ukim.finki.av1wp.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    UserService userService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    private HtmlUnitDriver driver;
    private static Category c1;
    private static Category c2;
    private static Manufacturer m1;
    private static Manufacturer m2;
    private static User regularUser;
    private static User adminUser;
    private static boolean dataInitialized = false;
    private static String user = "user";
    private static String admin = "admin";

    @BeforeEach
    public void setup(){
        this.driver = new HtmlUnitDriver(true);
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
            c1 = categoryService.create("c1", "c1");
            c2 = categoryService.create("c2", "c2");
            m1 = manufacturerService.save("m1", "m1").get();
            m2 = manufacturerService.save("m2", "m2").get();
            regularUser = userService.register(user, user, user, user, user, Role.ROLE_USER);
            adminUser = userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception{
        ProductsPage productsPage = ProductsPage.to(this.driver);
        productsPage.assertElements(0, 0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(driver);
        productsPage = LoginPage.doLogin(driver, loginPage, adminUser.getUsername(), admin);
        productsPage.assertElements(0, 0, 0, 0, 1);
        productsPage = AddOrEditProduct.addProduct(driver, "test", "100", "5", c2.getName(), m2.getName());
        productsPage.assertElements(1, 1, 1, 1, 1);
        productsPage = AddOrEditProduct.addProduct(driver, "test1", "200", "14", c1.getName(), m2.getName());
        productsPage.assertElements(2, 2, 2, 2, 1);
        productsPage.getDeleteButtons().get(1).click();
        productsPage.assertElements(1, 1, 1, 1, 1);
        productsPage = AddOrEditProduct.editProduct(driver,productsPage.getEditButtons().get(0), "test1", "200", "14", c1.getName(), m2.getName());
        productsPage.assertElements(1, 1, 1, 1, 1);
        loginPage = LoginPage.logout(driver);
        productsPage = LoginPage.doLogin(driver, loginPage, regularUser.getUsername(), user);
        productsPage.assertElements(1, 0, 0, 1, 0);
        productsPage.getCartButtons().get(0).click();
        Assert.assertEquals("url do not match", "http://localhost:9999/shopping-cart", this.driver.getCurrentUrl());
        ShoppingCartPage shoppingCartPage = ShoppingCartPage.init(driver);
        shoppingCartPage.assertElements(1);
    }
}

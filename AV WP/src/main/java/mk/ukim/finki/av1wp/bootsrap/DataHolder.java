package mk.ukim.finki.av1wp.bootsrap;

import mk.ukim.finki.av1wp.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Category> categories = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Manufacturer> manufacturers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();

    /*@PostConstruct
    public void init() {
        categories.add(new Category("Software", "Software Category"));
        categories.add(new Category("Books", "Books Category"));
        categories.add(new Category("Movies", "Movies Category"));
        Category category = new Category("Sport", "Sport Category");
        categories.add(category);
        users.add(new User("filip.kraljevski", "fk", "Filip", "Kraljevski"));
        users.add(new User("anastasija.kraljevska", "ak", "Anastasija", "Kraljevska"));
        Manufacturer manufacturer = new Manufacturer("Nike", "NY NY");
        manufacturers.add(manufacturer);
        manufacturers.add(new Manufacturer("Apple", "LA LA"));
        products.add(new Product("Ball", 350.0, 3, category, manufacturer));
        products.add(new Product("Harry Potter", 500.0, 3, category, manufacturer));
    }*/
}

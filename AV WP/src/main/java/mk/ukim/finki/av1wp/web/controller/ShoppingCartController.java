package mk.ukim.finki.av1wp.web.controller;

import mk.ukim.finki.av1wp.model.ShoppingCart;
import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        User user = (User) req.getSession().getAttribute("user");
        ShoppingCart shoppingCart = shoppingCartService.getActiveShoppingCart(user.getUsername());
        model.addAttribute("products", shoppingCartService.listAllProductsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @PostMapping("/add-product/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest req){
        try{
            User user = (User) req.getSession().getAttribute("user");
            ShoppingCart shoppingCart = shoppingCartService.addProductToShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        }catch (RuntimeException ex){
            return "redirect:/shopping-cart?error=" + ex.getMessage();
        }
    }
}

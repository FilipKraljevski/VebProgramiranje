package mk.ukim.finki.av1wp.web.controller;

import mk.ukim.finki.av1wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.av1wp.model.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.av1wp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,@RequestParam String password,@RequestParam String repeatedPassword,
                           @RequestParam String name,@RequestParam String surname){
        try {
            authService.register(username, password, repeatedPassword, name, surname);
            return "redirect:/login";
        }catch (PasswordDoNotMatchException | InvalidArgumentsException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}

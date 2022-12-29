package mk.ukim.finki.av1wp.web.controller;

import mk.ukim.finki.av1wp.model.Role;
import mk.ukim.finki.av1wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.av1wp.model.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.av1wp.service.AuthService;
import mk.ukim.finki.av1wp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final AuthService authService;
    private final UserService userService;

    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,@RequestParam String password,@RequestParam String repeatedPassword,
                           @RequestParam String name,@RequestParam String surname, @RequestParam Role role){
        try {
            userService.register(username, password, repeatedPassword, name, surname, role);
            return "redirect:/login";
        }catch (PasswordDoNotMatchException | InvalidArgumentsException exception){
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}

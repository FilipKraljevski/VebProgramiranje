package mk.ukim.finki.av1wp.service;

import mk.ukim.finki.av1wp.model.Role;
import mk.ukim.finki.av1wp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}

package mk.ukim.finki.av1wp.service;

import mk.ukim.finki.av1wp.model.User;

public interface AuthService {
    User login(String username, String password);

    User register(String username, String password, String repeatPassword, String name, String surname);
}

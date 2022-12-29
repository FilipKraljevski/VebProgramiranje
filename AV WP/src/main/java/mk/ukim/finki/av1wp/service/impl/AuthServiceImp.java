package mk.ukim.finki.av1wp.service.impl;

import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.av1wp.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.av1wp.model.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.av1wp.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.av1wp.repository.impl.InMemoryUserRepository;
import mk.ukim.finki.av1wp.repository.jpa.UserRepository;
import mk.ukim.finki.av1wp.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if(username == null || username.isEmpty() || password == null ||password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}

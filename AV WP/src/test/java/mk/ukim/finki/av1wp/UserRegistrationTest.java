package mk.ukim.finki.av1wp;

import mk.ukim.finki.av1wp.model.Role;
import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.av1wp.model.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.av1wp.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.av1wp.repository.jpa.UserRepository;
import mk.ukim.finki.av1wp.service.UserService;
import mk.ukim.finki.av1wp.service.impl.UserServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name", "surname", Role.ROLE_USER);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
        this.service = Mockito.spy(new UserServiceImp(this.userRepository, this.passwordEncoder));
    }

    @Test
    public void testSuccessRegister(){
        User user = this.service.register("username", "password", "password", "name",
                "surname", Role.ROLE_USER);
        Mockito.verify(this.service).register("username", "password", "password", "name",
                "surname", Role.ROLE_USER);
        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("name do not mach", "name", user.getName());
        Assert.assertEquals("role do not mach", Role.ROLE_USER, user.getRole());
        Assert.assertEquals("surname do not mach", "surname", user.getSurname());
        Assert.assertEquals("password do not mach", "password", user.getPassword());
        Assert.assertEquals("username do not mach", "username", user.getUsername());
    }



    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(null, "password", "password", "name",
                        "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(null, "password", "password", "name",
                "surname", Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, "password", "password", "name",
                        "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name",
                "surname", Role.ROLE_USER);
    }


    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surname",
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surname",
                Role.ROLE_USER);
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surname",
                        Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surname",
                Role.ROLE_USER);
    }


    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordDoNotMatchException.class,
                () -> this.service.register(username, password, confirmPassword, "name", "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, confirmPassword, "name", "surname", Role.ROLE_USER);
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "sureame", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.register(username, "password", "password", "name",
                        "surname", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name",
                "surname", Role.ROLE_USER);
    }

}

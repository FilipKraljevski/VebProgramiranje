package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumaration.Role;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.Imp.StudentServiceImp;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentLoginTest {
    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Student student = new Student("username", "password", "name", "surname", Role.ROLE_STUDENT);
        Mockito.when(studentRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(java.util.Optional.of(student));
        studentService = Mockito.spy(new StudentServiceImp(studentRepository, passwordEncoder));
    }

    @Test
    public void testSuccessLogin(){
        Student student = studentService.login("username", "password").orElse(null);
        Mockito.verify(studentService).login("username", "password");
        Assert.assertNotNull("student is null", student);
        Assert.assertEquals("password do not mach", "password", student.getPassword());
        Assert.assertEquals("username do not mach", "username", student.getUsername());
    }

    @Test
    public void testInvalidCredentials(){
        Assert.assertThrows("InvalidUserCredentialsException expected",
                InvalidUserCredentialsException.class, () -> this.studentService.login(null, "password"));
        Mockito.verify(studentService).login(null, "password");
    }
}

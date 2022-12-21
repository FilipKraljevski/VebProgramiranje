package mk.ukim.finki.av1wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Av1WpApplication {

	public static void main(String[] args) {
		SpringApplication.run(Av1WpApplication.class, args);
	}

}

package Main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
	}

	@Bean
	Path path(){
            return Paths.get("/data/finished-images");
	}
	
}

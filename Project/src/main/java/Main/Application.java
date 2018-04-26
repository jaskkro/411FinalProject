package Main;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static SessionManager sessionManager = new SessionManager();
    public static FinishedImageList finishedImages = new FinishedImageList();
    public static CleanImageList cleanImages = new CleanImageList();
    
	public static void main(String[] args) throws IOException {
            cleanImages = cleanImages.readJSONFromFile("clean.txt");
            finishedImages = finishedImages.readJSONFromFile("finished.txt");
            SpringApplication.run(Application.class, args);
	}
        
	
}

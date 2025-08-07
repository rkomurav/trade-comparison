import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Trade Document Comparison Tool application
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Trade Document Comparison Tool started successfully!");
        System.out.println("Access the application at http://localhost:8080");
    }
}
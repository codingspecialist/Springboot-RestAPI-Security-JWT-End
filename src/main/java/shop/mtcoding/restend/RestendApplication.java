package shop.mtcoding.restend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestendApplication {

    public static void main(String[] args) {
        // Sentry.io 연결해서 !!
        SpringApplication.run(RestendApplication.class, args);
    }

}

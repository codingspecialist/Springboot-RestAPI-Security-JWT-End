package shop.mtcoding.restend.core.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shop.mtcoding.restend.model.user.UserRepository;

@Component
public class DataInit extends DummyEntity{

    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return args -> {
            userRepository.save(newUser("ssar", "쌀"));
            userRepository.save(newUser("cos", "코스"));
        };
    }
}

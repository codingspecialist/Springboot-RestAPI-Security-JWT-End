package shop.mtcoding.restend.core.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.restend.model.user.User;

public class DummyEntity {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected User newUser(String username){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .fullName(username+"님")
                .email(username+"@nate.com")
                .role("USER")
                .status(true)
                .build();
    }
}
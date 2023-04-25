package shop.mtcoding.restend.model.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import shop.mtcoding.restend.core.dummy.DummyEntity;
import shop.mtcoding.restend.core.exception.Exception400;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

@Import(BCryptPasswordEncoder.class)
@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest extends DummyEntity {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        userRepository.save(newUser("ssar", "쌀"));
        userRepository.save(newUser("cos", "코스"));
        em.clear();
    }

    @Test
    public void findById() {
        // given
        Long id = 1L;

        // when
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isEmpty()) {
            throw new Exception400("id", "아이디를 찾을 수 없습니다");
        }
        User userPS = userOP.get();

        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1L);
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(
                passwordEncoder.matches("1234", userPS.getPassword())
        ).isEqualTo(true);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
        Assertions.assertThat(userPS.getFullName()).isEqualTo("쌀");
        Assertions.assertThat(userPS.getRole()).isEqualTo("USER");
        Assertions.assertThat(userPS.getStatus()).isEqualTo(true);
        Assertions.assertThat(userPS.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        Assertions.assertThat(userPS.getUpdatedAt()).isNull();
    }

    @Test
    public void findByUsername() {
        // given
        String username = "ssar";

        // when
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isEmpty()) {
            throw new Exception400("username", "유저네임을 찾을 수 없습니다");
        }
        User userPS = userOP.get();

        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1L);
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");
        Assertions.assertThat(
                passwordEncoder.matches("1234", userPS.getPassword())
        ).isEqualTo(true);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("ssar@nate.com");
        Assertions.assertThat(userPS.getFullName()).isEqualTo("쌀");
        Assertions.assertThat(userPS.getRole()).isEqualTo("USER");
        Assertions.assertThat(userPS.getStatus()).isEqualTo(true);
        Assertions.assertThat(userPS.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        Assertions.assertThat(userPS.getUpdatedAt()).isNull();
    }

    @Test
    public void save() {
        // given
        User love = newUser("love", "러브");

        // when
        User userPS = userRepository.save(love);

        // then (beforeEach에서 2건이 insert 되어 있음)
        Assertions.assertThat(userPS.getId()).isEqualTo(3L);
        Assertions.assertThat(userPS.getUsername()).isEqualTo("love");
        Assertions.assertThat(
                passwordEncoder.matches("1234", userPS.getPassword())
        ).isEqualTo(true);
        Assertions.assertThat(userPS.getEmail()).isEqualTo("love@nate.com");
        Assertions.assertThat(userPS.getFullName()).isEqualTo("러브");
        Assertions.assertThat(userPS.getRole()).isEqualTo("USER");
        Assertions.assertThat(userPS.getStatus()).isEqualTo(true);
        Assertions.assertThat(userPS.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        Assertions.assertThat(userPS.getUpdatedAt()).isNull();
    }
}

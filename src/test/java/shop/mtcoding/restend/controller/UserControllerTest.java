package shop.mtcoding.restend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.restend.core.auth.jwt.MyJwtProvider;
import shop.mtcoding.restend.core.dummy.DummyEntity;
import shop.mtcoding.restend.dto.user.UserRequest;
import shop.mtcoding.restend.model.user.UserRepository;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends DummyEntity {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        userRepository.save(newUser("ssar"));
        em.clear();
    }

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinInDTO joinInDTO = new UserRequest.JoinInDTO();
        joinInDTO.setUsername("cos");
        joinInDTO.setPassword("1234");
        joinInDTO.setEmail("cos@nate.com");
        joinInDTO.setFullName("코스");
        String requestBody = om.writeValueAsString(joinInDTO);

        // when
        ResultActions resultActions = mvc
                .perform(post("/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginInDTO loginInDTO = new UserRequest.LoginInDTO();
        loginInDTO.setUsername("cos");
        loginInDTO.setPassword("1234");
        String requestBody = om.writeValueAsString(loginInDTO);

        // when
        ResultActions resultActions = mvc
                .perform(post("/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        String jwtToken = resultActions.andReturn().getResponse().getHeader(MyJwtProvider.HEADER);
        Assertions.assertThat(jwtToken.startsWith(MyJwtProvider.TOKEN_PREFIX)).isTrue();
        resultActions.andExpect(status().isOk());
    }

    // jwt token -> 인증필터 -> 시큐리티 세션생성
    // setupBefore=TEST_METHOD (setUp 메서드 실행전에 수행)
    // setupBefore=TEST_EXECUTION (saveAccount_test 메서드 실행전에 수행)
    // @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=ssar 조회를 해서
    // 세션에
    // 담아주는 어노테이션!!
    // UserDetail 테스트 하기

}

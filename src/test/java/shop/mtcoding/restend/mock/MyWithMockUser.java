package shop.mtcoding.restend.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MyWithMockUserFactory.class)
public @interface MyWithMockUser {
    long id() default 1L;
    String username() default "cos";
    String role() default "USER";
    String fullName() default "코스";
}

package shop.mtcoding.restend.core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterRegisterConfig {
    //@Bean
    public FilterRegistrationBean<?> filter1() {
        FilterRegistrationBean<?> registration = new FilterRegistrationBean<>();
        registration.setFilter(null); // 서블릿 필터 객체 담기
        registration.addUrlPatterns("/주소/*");
        registration.setOrder(1);
        return registration;
    }
    //@Bean
    public FilterRegistrationBean<?> filter2() {
        FilterRegistrationBean<?> registration = new FilterRegistrationBean<>();
        registration.setFilter(null); // 서블릿 필터 객체 담기
        registration.addUrlPatterns("/주소/*");
        registration.setOrder(2);
        return registration;
    }
}

package shop.mtcoding.restend.core.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import shop.mtcoding.restend.core.auth.session.MyUserDetails;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class MyLogAdvice {

    // private final ErrorLogRepository errorLogRepository;
    // private final LogRepository logRepository;

    @Pointcut("@annotation(shop.mtcoding.restend.core.annotation.MyLog)")
    public void myLog(){}

    @Pointcut("@annotation(shop.mtcoding.restend.core.annotation.MyErrorLog)")
    public void myErrorLog(){}

    @AfterReturning("myLog()")
    public void logAdvice(JoinPoint jp) throws Exception {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        log.debug("디버그 : "+method.getName()+" 성공");
    }

    @Before("myErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                log.error("에러 : "+e.getMessage());
            }
        }
    }
}
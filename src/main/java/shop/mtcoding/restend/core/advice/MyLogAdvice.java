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

    // 서비스 메서드에 붙여서 서비스 에러시 로그 받기 (추후 DB로 저장하거나, 파일로 옮기면됨)
    @AfterReturning("myLog()")
    public void logAdvice(JoinPoint jp) throws HttpMessageNotReadableException {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        log.debug("디버그 : "+method.getName()+" 성공");
    }

    // 500 에러시에 붙이기 (추후 DB로 저장하거나, 파일로 옮기면됨)
    @Before("myErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws HttpMessageNotReadableException {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication();

                if(myUserDetails != null){
                    log.error("에러 : "+e.getMessage());
                }
            }
        }
    }
}
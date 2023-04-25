package shop.mtcoding.restend.core.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class MyTempFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("디버그 : MyTempFilter 동작");
        chain.doFilter(request, response);
    }
}

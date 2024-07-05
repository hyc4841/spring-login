package hello.login.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request; // ServletRequest는 인터페이스 여서 자체에는 별다른 기능이 없기 때문에 HttpServletRequest로 다운 캐스팅 해줘햐함

        String requestURI = httpRequest.getRequestURI(); // 요청 URI
        String uuid = UUID.randomUUID().toString();     // 요청 구분용 UUID

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            filterChain.doFilter(request, response); // 다음 필터 있으면 다음 필터 호출, 없으면 서블릿 호출
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI); // finally는 마지막에 항상 호출된다.
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }


}

package com.hana.chagokchagok.util;

import com.hana.chagokchagok.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    private final String HEADER_AUTH = "Authorization";

    private JWTUtil jwtUtil;

    public JwtInterceptor(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader(HEADER_AUTH);

        if (request.getRequestURI().equals("/admin/login")) {
            return true;
        }

        if (token != null && jwtUtil.checkToken(token)) {
            log.info("토큰 사용 가능 : {}", token);
            return true;
        } else {
            log.info("토큰 사용 불가능 : {}", token);
            throw new UnAuthorizedException();
        }
    }

}
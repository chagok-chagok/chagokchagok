package com.hana.chagokchagok.util;

import com.hana.chagokchagok.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

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
        System.out.println("검증할 토큰!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+token);
        System.out.println(request.getMethod());

        String requestUrl = request.getRequestURL().toString();
        System.out.println("요청된 URL: " + requestUrl);

        if (CorsUtils.isPreFlightRequest(request)) {
            System.out.println("옵션"+CorsUtils.isPreFlightRequest(request));
            return true;
        }

        if ("GET".equals(request.getMethod()) && "text/event-stream".equals(request.getContentType())) {
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
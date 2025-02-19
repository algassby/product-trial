package com.barry.product.aspect;

import com.barry.product.repository.UserRepository;
import com.barry.product.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckCurrentUserIdentificationAspect {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Pointcut("@annotation(com.barry.product.annotations.CheckCurrentUserIdentification")
    public void  checkId(){}

    @Before("checkId()")
    public void checkCurrentId(JoinPoint joinPoint){

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


        String queryParams = request.getQueryString();


        var userId = jwtUtil.extractUserId(jwtUtil.extractTokenFromJwt(request));
        if(!userId.equals(queryParams)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'est pas autorisé à éffectuer cette action.");
        }

    }

    private String extractHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }
        return headers.toString();
    }
}

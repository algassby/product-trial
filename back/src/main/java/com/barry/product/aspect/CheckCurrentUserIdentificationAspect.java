package com.barry.product.aspect;

import com.barry.product.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckCurrentUserIdentificationAspect {

    private final JwtUtil jwtUtil;

    @Pointcut("@annotation(com.barry.product.annotations.CheckCurrentUserIdentification))")
    public void  checkIdentification(){}

    @Before("checkIdentification()")
    public void checkCurrentId(JoinPoint joinPoint){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = method.getParameters();
        String userId = null;

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(PathVariable.class) && parameter.getName().equals("userId")) {
                Object value = args[i];
                if (value != null) {
                    userId = value.toString(); // On convertit directement en String
                }
            }

            if (parameter.isAnnotationPresent(RequestBody.class)) {
                Object requestBody = args[i];
                userId = extractFieldValue(requestBody, "userId");
            }
        }

        // Récupération de la requête HTTP pour extraire le JWT
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            String jwt = jwtUtil.extractTokenFromJwt(request);
            String userIdFromJwt = jwtUtil.extractUserId(jwt);

            if (userIdFromJwt != null && userId != null && !Objects.equals(userId, userIdFromJwt)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'êtes pas autorisé à effectuer cette action.");
            }
        }

    }

    /**
     * Récupère l'objet HttpServletRequest actuel.
     * @return HttpServletRequest ou null si non disponible.
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    private String extractFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(obj);
            return value != null ? value.toString() : null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

}

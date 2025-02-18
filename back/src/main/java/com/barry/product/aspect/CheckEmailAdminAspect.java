package com.barry.product.aspect;

import com.barry.product.utils.predicate.CheckEmailAdminPredicate;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckEmailAdminAspect {

    private final CheckEmailAdminPredicate checkEmailAdminPredicate;

    @Pointcut("@annotation(com.barry.product.annotations.CheckEmail)")
    public void checkEmail() {}

    @Before("checkEmail()")
    public void checkIsEmailAdmin(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'est pas autorisé à éffectuer cette action.");
        }
        if (!checkEmailAdminPredicate.test(email)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vous n'est pas autorisé à éffectuer cette action.");
        }
    }

}

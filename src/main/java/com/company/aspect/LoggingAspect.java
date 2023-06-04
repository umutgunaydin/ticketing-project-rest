package com.company.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //  Logger logger= LoggerFactory.getLogger(LoggingAspect.class); // did it with lombok

    private String getUsername() { // to get logged in user username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.company.controller.ProjectController.*(..)) || execution(* com.company.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC() {
    }

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint) {
        log.info("Before -> Method: {}, User: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername());
    }

    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()",returning = "results")
    public void afterReturningAnyProjevtAndTaskControllerAdvice(JoinPoint joinPoint,Object results){
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
        , joinPoint.getSignature().toShortString()
        ,getUsername()
        ,results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()",throwing = "exception")
    public void afterReturningAnyProjevtAndTaskControllerAdvice(JoinPoint joinPoint,Exception exception){
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                ,getUsername()
                ,exception.getMessage());
    }

}

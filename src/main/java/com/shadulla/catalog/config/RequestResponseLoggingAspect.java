package com.shadulla.catalog.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
@Slf4j
public class RequestResponseLoggingAspect {

    @Before("execution(* com.shadulla.catalog.modules.categories.adapter.web.in.*.*(..))")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (request != null) {
            StringBuilder message = new StringBuilder();
            message.append("Request Method: ").append(request.getMethod())
                    .append(" | Request URL: ").append(request.getRequestURL())
                    .append(" | Headers: ").append(request.getHeaderNames());

            log.info(message.toString());
        }
    }

    @AfterReturning(value = "execution(* com.shadulla.catalog.modules.categories.adapter.web.in.*.*(..))", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Response from method {}: {}", methodName, response);
    }
}

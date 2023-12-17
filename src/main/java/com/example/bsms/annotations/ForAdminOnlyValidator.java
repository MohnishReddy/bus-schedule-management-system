package com.example.bsms.annotations;

import com.example.bsms.models.responses.BasicResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.example.bsms.constants.AuthConstants.CONTEXT_ROLE_KEY;
import static com.example.bsms.constants.RoleConstants.ADMIN;

@Aspect
@Component
public class ForAdminOnlyValidator {



    @Around("@annotation(forAdminOnly)")
    public Object validateMethod(ProceedingJoinPoint joinPoint, ForAdminOnly forAdminOnly) throws Throwable {
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = null;
        for(Object arg: args) {
            if(arg instanceof HttpServletRequest)
                request = (HttpServletRequest) arg;
        }

        // Perform your custom method-level validation
        if (isValid(request)) {
            return joinPoint.proceed();
        } else {
            return new ResponseEntity<BasicResponse>(new BasicResponse() {{
                setErrMessage(forAdminOnly.errMessage());
            }}, HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isValid(HttpServletRequest request) {
        return request != null && request.getAttribute(CONTEXT_ROLE_KEY).equals(ADMIN);
    }
}

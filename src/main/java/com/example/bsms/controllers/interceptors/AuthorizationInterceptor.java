package com.example.bsms.controllers.interceptors;

import com.example.bsms.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.example.bsms.constants.AuthConstants.*;

@Component
@AllArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register"))
            return true;

        String authHeaderData = request.getHeader(AUTH_HEADER);
        if( authHeaderData == null ) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String[] authData = authHeaderData.split(" ");
        if(!authData[0].equals(AUTH_PREFIX) || authData.length != 2) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            String username = authenticationService.getUniqueIdFromToken(authData[1]);
            String role = authenticationService.getRoleFromUsername(username);
            request.setAttribute(CONTEXT_USERNAME_KEY, username);
            request.setAttribute(CONTEXT_ROLE_KEY, role);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

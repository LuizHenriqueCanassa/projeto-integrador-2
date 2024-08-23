package br.com.luizcanassa.projetintegrador2.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class LoginFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if (isAuthenticated() && "/login".equals(servletRequest.getRequestURI())) {

            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
                    servletRequest.getContextPath() + "/dashboard");

            servletResponse.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            servletResponse.setHeader("Location", encodedRedirectURL);
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }

        return authentication.isAuthenticated();
    }

}

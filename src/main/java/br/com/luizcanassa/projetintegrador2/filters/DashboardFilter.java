package br.com.luizcanassa.projetintegrador2.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class DashboardFilter extends GenericFilterBean {
    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().equals("/")) {
            String encodedRedirectURL = ((HttpServletResponse) servletResponse).encodeRedirectURL(
                    request.getContextPath() + "/dashboard");

            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.sendRedirect(encodedRedirectURL);
        }

        filterChain.doFilter(request, response);
    }
}

package com.cvg.cya.postulacion.filters;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginPageFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (
                SecurityContextHolder.getContext().getAuthentication() != null &&
                        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                        ((HttpServletRequest) servletRequest).getRequestURI().equals("/login")
        ) {
            ((HttpServletResponse)servletResponse).sendRedirect("/");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
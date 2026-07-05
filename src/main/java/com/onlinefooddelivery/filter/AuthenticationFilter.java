package com.onlinefooddelivery.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the request URI
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Allow public pages (no authentication required)
        boolean isPublicPage = 
            uri.equals(contextPath + "/") ||
            uri.equals(contextPath + "/index.jsp") ||
            uri.startsWith(contextPath + "/auth/") ||
            uri.startsWith(contextPath + "/assets/") ||
            uri.equals(contextPath + "/login") ||
            uri.equals(contextPath + "/register");

        // Allow static resources
        boolean isStaticResource = 
            uri.startsWith(contextPath + "/assets/css/") ||
            uri.startsWith(contextPath + "/assets/js/") ||
            uri.startsWith(contextPath + "/assets/images/");

        if (isPublicPage || isStaticResource) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("userId") != null);

        if (!isLoggedIn) {
            // Redirect to login page
            httpResponse.sendRedirect(contextPath + "/auth/login.jsp");
            return;
        }

        // User is authenticated, continue
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
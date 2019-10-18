package com.nixsolutions.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        String path;

        String[] adminPages = {"/admin", "/add", "/edit"};
        if (req.getQueryString() != null) {
            path = uri + "?" + req.getQueryString();
        } else {
            path = uri;
        }
        if (path.startsWith("/resources/")) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (session == null) {
            if (path.contains("/login")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resp.sendRedirect("/login");
                return;
            }

        } else if (session.getAttribute("auth_admin") == null
            && session.getAttribute("auth_user") == null) {
            filterChain.doFilter(req, resp);
        } else if (path.contains("/logout")) {
            filterChain.doFilter(req, resp);
        } else if (session.getAttribute("auth_admin") != null) {
            for (String url: adminPages) {
                if (path.startsWith(url)) {
                    System.out.println(path);
                    req.getRequestDispatcher(path).forward(req, resp);
                    return;
                }
            }
            if (path.contains("/error")) {
                req.getRequestDispatcher(path).forward(req, resp);
                return;
            }
            resp.sendRedirect("/admin");
        } else if (session.getAttribute("auth_user") != null) {
            System.out.println(path);
            if (path.startsWith("/user")) {
                req.getRequestDispatcher(path).forward(req, resp);
                return;
            }
            if (path.contains("/error")) {
                req.getRequestDispatcher(path).forward(req, resp);
                return;
            }
            req.setAttribute("user_name", session.getAttribute("auth_user"));
            resp.sendRedirect("/user");
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}

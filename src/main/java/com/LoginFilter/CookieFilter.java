package com.LoginFilter;

import getInfoLogin.IdUser;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.http.*;
@WebFilter(filterName = "CookieFilter",urlPatterns = "/*")
public class CookieFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String header = ((HttpServletRequest) req).getHeader("ajax");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(header!=null){

            if(( auth.getAuthorities().toString()).equals("[ROLE_ANONYMOUS]")){
                HttpServletResponse httpResponse = (HttpServletResponse) resp;
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                chain.doFilter(req, resp);
            }else{
                chain.doFilter(req, resp);
            }
        }else{
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}

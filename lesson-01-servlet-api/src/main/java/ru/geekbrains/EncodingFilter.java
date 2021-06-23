package ru.geekbrains;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig _filterConfig) throws ServletException {
        this.filterConfig = _filterConfig;
    }

    @Override
    public void doFilter(ServletRequest _servletRequest, ServletResponse _servletResponse, FilterChain _filterChain) throws IOException, ServletException {
        _servletResponse.setContentType("text/html");
        _servletResponse.setCharacterEncoding("UTF-8");

        _filterChain.doFilter(_servletRequest, _servletResponse);
    }

    @Override
    public void destroy() {

    }
}

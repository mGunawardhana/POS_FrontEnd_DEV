package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CopyWriteOwner - mr.Gunawardhana
 * Contact - 071 - 733 1792
 * <p>
 * © 2022 mGunawardhana,INC. ALL RIGHTS RESERVED.
 */
@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;
        servletResponse1.addHeader("Access-Control-Allow-Origin", "*");
        servletResponse1.addHeader("Access-Control-Allow-Methods", "DELETE,PUT");
        servletResponse1.addHeader("Access-Control-Allow-Headers", "Content-Type");
        servletResponse1.setContentType("application/json");
    }

    @Override
    public void destroy() {

    }
}

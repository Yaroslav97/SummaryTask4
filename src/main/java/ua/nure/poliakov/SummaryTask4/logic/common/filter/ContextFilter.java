package ua.nure.poliakov.SummaryTask4.logic.common.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "profiler", urlPatterns = {"/*"})
public class ContextFilter implements Filter {

    private static final Logger log = Logger.getLogger(ContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Start filter application");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Locale.setDefault(Locale.ENGLISH);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.debug("Destroy filter application");
    }
}

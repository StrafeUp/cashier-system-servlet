package cashiersystem.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LanguageFilter implements Filter {

    public static final String DEFAULT_LANGUAGE = "en";
    public static final String SESSION_LOCALE_PARAMETER = "lang";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter(SESSION_LOCALE_PARAMETER) != null) {
            req.getSession().setAttribute(SESSION_LOCALE_PARAMETER, req.getParameter(SESSION_LOCALE_PARAMETER));
        } else if (req.getSession(true).getAttribute(SESSION_LOCALE_PARAMETER) != null) {
            req.getSession().setAttribute(SESSION_LOCALE_PARAMETER, req.getSession().getAttribute(SESSION_LOCALE_PARAMETER));
        } else {
            req.getSession().setAttribute(SESSION_LOCALE_PARAMETER, DEFAULT_LANGUAGE);
        }
        chain.doFilter(req, response);
    }
}

package time.servlets;

import time.common.Constants;
import time.utils.TimezoneUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
    private final TimezoneUtil timezoneUtil = new TimezoneUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {

            String timezoneParameter = httpRequest.getParameter(Constants.TIMEZONE_PARAM);
            if (timezoneParameter != null) {
                if (timezoneUtil.getTimeZoneFromRequest(timezoneParameter) == null) {
                    httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    try (PrintWriter writer = httpResponse.getWriter()) {
                        writer.write("Invalid timezone");
                    } catch (IOException e) {
                        throw new ServletException("Error while writing to response", e);
                    }
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

}

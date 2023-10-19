package time.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import time.utils.TimezoneUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static time.common.Constants.*;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private final TimezoneUtil timezoneUtil = new TimezoneUtil();
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String timezoneParameter = request.getParameter(TIMEZONE_PARAM);
        TimeZone timeZone;

        if (timezoneParameter != null) {
            timezoneParameter = URLEncoder.encode(timezoneParameter, "UTF-8");
            Cookie cookie = new Cookie(LAST_TIMEZONE_COOKIE, timezoneParameter);
            response.addCookie(cookie);
        }

        Cookie[] cookies = request.getCookies();
        String lastTimezone = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (LAST_TIMEZONE_COOKIE.equals(cookie.getName())) {
                    lastTimezone = cookie.getValue();
                    break;
                }
            }
        }

        if (lastTimezone != null) {
            timeZone = timezoneUtil.getTimeZoneFromRequest(lastTimezone);
        } else {
            timeZone = TimeZone.getTimeZone(UTC_TIMEZONE);
        }

        String formattedDate = formatCurrentDateWithTimeZone(timeZone);

        try (PrintWriter out = response.getWriter()) {
            Context context = new Context();
            context.setVariable("time", formattedDate);

            engine.process("time-template", context, out);
        }
    }

    private String formatCurrentDateWithTimeZone(TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);
        Date now = new Date();
        return dateFormat.format(now);
    }

}

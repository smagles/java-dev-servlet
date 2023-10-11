package time.servlets;

import time.common.Constants;
import time.utils.TimezoneUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static time.common.Constants.UTC_TIMEZONE;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private final TimezoneUtil timezoneUtil = new TimezoneUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timezoneParameter = request.getParameter(Constants.TIMEZONE_PARAM);
        TimeZone timeZone;

        if (timezoneParameter != null) {
            timeZone = timezoneUtil.getTimeZoneFromRequest(timezoneParameter);
        } else {
            timeZone = TimeZone.getTimeZone(UTC_TIMEZONE);
        }

        String formattedDate = formatCurrentDateWithTimeZone(timeZone);

        request.setAttribute("time", formattedDate);
        request.getRequestDispatcher("/time-view.jsp").forward(request, response);
    }

    private String formatCurrentDateWithTimeZone(TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZone);
        Date now = new Date();
        return dateFormat.format(now);
    }

}

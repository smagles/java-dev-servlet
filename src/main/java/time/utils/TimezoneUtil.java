package time.utils;

import java.util.TimeZone;

import static time.common.Constants.UTC_TIMEZONE;

public class TimezoneUtil {
    public TimeZone getTimeZoneFromRequest(String timezoneParameter) {
        if (timezoneParameter.startsWith(UTC_TIMEZONE)) {
            String offset = timezoneParameter.substring(3);
            String timeZoneID = findTimeZoneByUTCOffset(offset);
            if (timeZoneID != null) {
                return TimeZone.getTimeZone(timeZoneID);
            }
        }
        return null;
    }

    private String findTimeZoneByUTCOffset(String offsetParameter) {

        char sign = offsetParameter.charAt(0);
        int offset = Integer.parseInt(offsetParameter.substring(1));
        if (sign == '-') {
            offset = -offset;
        }

        String[] availableIDs = TimeZone.getAvailableIDs();
        for (String id : availableIDs) {
            TimeZone timeZone = TimeZone.getTimeZone(id);
            if (timeZone.getRawOffset() == offset * 3600000) {
                return id;
            }

        }
        return null;
    }

}

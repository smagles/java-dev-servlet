package time.utils;

import time.common.Constants;

import java.util.TimeZone;

public class TimezoneUtil {
    public TimeZone getTimeZoneFromRequest(String timezoneParameter) {
        if (timezoneParameter != null && timezoneParameter.startsWith(Constants.UTC_TIMEZONE)) {
            String offset = timezoneParameter.substring(3);
            String timeZoneID = findTimeZoneByUtcOffset(offset);
            if (timeZoneID != null) {
                return TimeZone.getTimeZone(timeZoneID);
            }
        }
        return null;
    }

    private String findTimeZoneByUtcOffset(String offsetParameter) {

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

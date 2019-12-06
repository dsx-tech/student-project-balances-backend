package dsx.bcv.server.services.parsers;

import java.time.LocalDateTime;

class DateFormatter {

    static LocalDateTime getDateTimeFromString(String dateString) {

        var spacePos = dateString.indexOf(' ');
        if (dateString.charAt(spacePos + 2) == ':') {
            dateString = dateString.substring(0, spacePos + 1) + '0' + dateString.substring(spacePos + 1);
        }

        final String localDateTimeFormat = dateString.replace(' ', 'T');
        return LocalDateTime.parse(localDateTimeFormat);
    }
}

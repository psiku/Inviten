package com.inviten.api.authorization.converter;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeConverter {

    public  String convertTimestampToIso8601(long timestamp) {
        Date expirationDate = new Date(timestamp);
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return iso8601Format.format(expirationDate);
}
}

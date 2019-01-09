package com.korotaev.r.ms.hor.AppHelpers;

public class ParserHelper {

    public static Long TryParse(Object obj) {
        Long retVal;
        try {
            retVal = Long.parseLong((String) obj);
        } catch (NumberFormatException nfe) {
            retVal = 0L;
        }
        return retVal;
    }
}

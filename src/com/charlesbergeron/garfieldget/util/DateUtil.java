package com.charlesbergeron.garfieldget.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-30
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static  String format(String format, GregorianCalendar currentDate) {
        return new SimpleDateFormat(format).format(currentDate.getTime());
    }

}

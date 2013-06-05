package com.charlesbergeron.garfieldget.comics;

import org.htmlparser.util.ParserException;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-28
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public interface Comic {
    public abstract GregorianCalendar getFirstStripDate();
    public abstract String getUrl(GregorianCalendar date);
    public abstract String getName();
}

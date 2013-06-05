package com.charlesbergeron.garfieldget.comics;

import org.htmlparser.util.ParserException;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-28
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class Mulligan extends AbstractComics implements Comic {

    private final GregorianCalendar firstStrip = new GregorianCalendar(2013, 4, 13);
    private final String prefixUrl = "http://www.gocomics.com/mulligan/";

    private final String COMIC_LABEL= "Mulligan";

    @Override
    public String getName() {
        return COMIC_LABEL;
    }

    @Override
    public GregorianCalendar getFirstStripDate() {
        return firstStrip;
    }

    @Override
    public String getUrl(GregorianCalendar date) {
        return getUrl(prefixUrl, "alt", COMIC_LABEL, date);
    }

}

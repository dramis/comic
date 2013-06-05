package com.charlesbergeron.garfieldget.comics;

import org.htmlparser.util.ParserException;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-28
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class Dilbert extends AbstractComics implements Comic {

    private final GregorianCalendar firstStrip = new GregorianCalendar(2012, 5, 17);
    private final String prefixUrl = "http://www.gocomics.com/dilbert-classics/";

    private final String COMIC_LABEL = "Dilbert";
    private final String COMIC_IMG_LABEL = "Dilbert Classics";

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
        return getUrl(prefixUrl, "alt", COMIC_IMG_LABEL, date);
    }


}

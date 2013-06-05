package com.charlesbergeron.garfieldget.comics;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-28
 * Time: 12:31
 */
public abstract class AbstractComics {

    protected String getUrl(String prefixUrl, String attribute, String value, GregorianCalendar date) {

        Parser parser = new Parser ();

        // for a simple dump, use more verbose settings
        //parser.setFeedback (Parser.STDOUT);
        try {
            parser.setResource (generateUrl(prefixUrl, date));

            NodeFilter nodeFilter[] = new NodeFilter[] {
                    new TagNameFilter("img"),
                    new HasAttributeFilter(attribute, value)
            };
            AndFilter orFilter = new AndFilter(nodeFilter);

            SimpleNodeIterator iterator = parser.parse(orFilter).elements();
            while(iterator.hasMoreNodes()) {
                Node node = iterator.nextNode();
                if (node instanceof ImageTag) {
                    ImageTag imageTag = (ImageTag)node;
                    return imageTag.getImageURL();
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public String generateUrl(String prefixUrl, GregorianCalendar date) {
        String year = Integer.toString(date.get(GregorianCalendar.YEAR));
        String month = Integer.toString(date.get(GregorianCalendar.MONTH) + 1);
        String day = Integer.toString(date.get(GregorianCalendar.DAY_OF_MONTH));

        if (month.length() == 1) {month = "0" + month;}
        if (day.length() == 1) {day = "0" + day;}

        return prefixUrl + year + "/" + month + "/" + day;
    }
}

package com.charlesbergeron.garfieldget.util;

import org.htmlparser.util.ParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-30
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {

    public static File writeCreateFile(URL url, String path) {

        File file = new File(path);
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
            return file;
        } catch (IOException ignored) {}

        return null;
    }

}

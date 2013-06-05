package com.charlesbergeron.garfieldget.factory;

import com.charlesbergeron.garfieldget.comics.Comic;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-30
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class ComicFactory {
    private static final String COMICS_CLASS_PACKAGE = "com.charlesbergeron.garfieldget.comics";
    private static final Map<String, Comic> COMIC_MAP = new HashMap<>();

    public static Comic makeComic(String comicName) {
        assert COMIC_MAP.containsKey(comicName);
        return COMIC_MAP.get(comicName);
    }

    public static void initFactory()  {
        Reflections reflections = new Reflections(COMICS_CLASS_PACKAGE);
        reflections.getSubTypesOf(Comic.class).forEach(comicClass -> {
            try {
                Comic comic = comicClass.newInstance();
                COMIC_MAP.put(comic.getName(), comic);
            } catch (InstantiationException | IllegalAccessException e) {/*Class not load, did not add to list*/}
        });
    }

    public static List<String> getComicsNameList() {
        ArrayList<String> comicsName = new ArrayList<>();
        COMIC_MAP.forEach((comicStr, comic) -> { comicsName.add(comic.getName()); });
        comicsName.sort(String::compareTo);
        return comicsName;
    }
}

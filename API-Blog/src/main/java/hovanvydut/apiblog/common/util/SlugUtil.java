package hovanvydut.apiblog.common.util;

import com.github.slugify.Slugify;

/**
 * @author hovanvydut
 * Created on 6/30/21
 */

public class SlugUtil {

    // Explicitly declare private constructor to avoid default public constructor so user can't instant new object
    private SlugUtil() {

    }

    public static String slugify(String text) {
        Slugify slugify = new Slugify();
        return slugify.slugify(text);
    }

}

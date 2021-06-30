package hovanvydut.apiblog.common.util;

import com.github.slugify.Slugify;

/**
 * @author hovanvydut
 * Created on 6/30/21
 */

public class SlugUtil {

    public static String slugify(String text) {
        Slugify slugify = new Slugify();
        return slugify.slugify(text);
    }

}

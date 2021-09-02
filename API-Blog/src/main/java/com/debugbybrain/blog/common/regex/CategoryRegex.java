package com.debugbybrain.blog.common.regex;

/**
 * @author hovanvydut
 * Created on 7/28/21
 */

public class CategoryRegex {

    public static class slug {
        public static final String pattern = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
        public static final String message = "Only include character a-z,0-9 and - between words";
    }

}

package hovanvydut.apiblog.common.regex;

/**
 * @author hovanvydut
 * Created on 7/28/21
 */

public class TagRegex {
    public static class name {
        public static final String pattern = "^[a-zA-Z0-9]+(?:(\\s|-)[a-zA-Z0-9]+)*$";
        public static final String message = "Only include character a-z,A-Z,0-9 and space between words";
    }

    public static class slug {
        public static final String pattern = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
        public static final String message = "Only include character a-z,0-9 and - between words";
    }
}

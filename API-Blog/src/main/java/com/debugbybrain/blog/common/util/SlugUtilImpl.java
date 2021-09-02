package com.debugbybrain.blog.common.util;

import com.github.slugify.Slugify;

/**
 * @author hovanvydut
 * Created on 8/25/21
 */

public class SlugUtilImpl implements SlugUtil{
    @Override
    public String slugify(String text) {
        Slugify slugify = new Slugify();
        return slugify.slugify(text);
    }
}

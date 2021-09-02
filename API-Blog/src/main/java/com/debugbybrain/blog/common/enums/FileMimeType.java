package com.debugbybrain.blog.common.enums;

/**
 * @author hovanvydut
 * Created on 7/7/21
 */

public enum FileMimeType {
    JPEG("image/jpeg"), JPG("image/jpeg"), PNG("image/png"), GIF("image/gif");

    String typeName;

    private FileMimeType(String typeName) {
        this.typeName = typeName;
    }

    public String get() {
        return this.typeName;
    }
}

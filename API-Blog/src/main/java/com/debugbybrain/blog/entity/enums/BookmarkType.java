package com.debugbybrain.blog.entity.enums;

/**
 * @author hovanvydut
 * Created on 8/15/21
 */

public enum BookmarkType {
    POST(0), SERIES(1);

    private int type;

    BookmarkType(int type) {
        this.type = type;
    }

    public int type() {
        return this.type;
    }

    public static BookmarkType of(int type) {
        switch (type) {
            case 0:
                return POST;
            case 1:
                return SERIES;
            default:
                throw new RuntimeException("Not match any of ArticleType");
        }
    }
}

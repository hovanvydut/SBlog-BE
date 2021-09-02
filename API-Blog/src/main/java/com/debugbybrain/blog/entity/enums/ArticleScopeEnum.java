package com.debugbybrain.blog.entity.enums;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

public enum ArticleScopeEnum {
    GLOBAL(0),
    ONLY_WHO_HAS_LINK(1);

    private int scope;

    ArticleScopeEnum(int scope) {
        this.scope = scope;
    }

    public int get() {
        return this.scope;
    }
}

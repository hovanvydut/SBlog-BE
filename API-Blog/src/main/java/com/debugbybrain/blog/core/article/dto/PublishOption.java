package com.debugbybrain.blog.core.article.dto;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

public enum PublishOption {

    DRAFT("DRAFT"),
    GLOBAL_PUBLISH("GLOBAL_PUBLISHED"),
    PRIVATE_LINK_PUBLISH("PRIVATE_LINK_PUBLISH");

    private String scope;

    PublishOption(String scope) {
        this.scope = scope;
    }

    public static PublishOption of(String scope) {
        switch (scope) {
            case "DRAFT":
                return PublishOption.DRAFT;
            case "GLOBAL_PUBLISHED":
                return PublishOption.GLOBAL_PUBLISH;
            case "PRIVATE_LINK_PUBLISH":
                return PublishOption.PRIVATE_LINK_PUBLISH;
            default:
                throw new RuntimeException("Can't convert to PublishOption");
        }
    }
}

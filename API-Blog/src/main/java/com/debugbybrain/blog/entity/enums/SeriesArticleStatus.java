package com.debugbybrain.blog.entity.enums;

/**
 * @author hovanvydut
 * Created on 8/29/21
 */

public enum SeriesArticleStatus {
    ACCEPTED(0), REJECTED(1), PENDING(2);

    private int status;

    SeriesArticleStatus(int status) {
        this.status = status;
    }

    public int get() {
        return this.status;
    }

    public SeriesArticleStatus of (int status) {
        switch (status) {
            case 0:
                return SeriesArticleStatus.ACCEPTED;
            case 1:
                return SeriesArticleStatus.REJECTED;
            case 2:
                return SeriesArticleStatus.PENDING;
            default:
                throw new RuntimeException("Status is not mapped");
        }
    }
}

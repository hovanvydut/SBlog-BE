package hovanvydut.apiblog.entity.enums;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

public enum ArticleStatusEnum {
    DRAFT(0),
    SPAM(1),
    PENDING(2),
    PUBLISHED_GLOBAL(3),
    PUBLISHED_LINK(4);

    private int status;

    ArticleStatusEnum(int status) {
        this.status = status;
    }

    public int get() {
        return this.status;
    }
}

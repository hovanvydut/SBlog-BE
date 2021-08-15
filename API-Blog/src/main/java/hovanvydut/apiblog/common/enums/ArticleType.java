package hovanvydut.apiblog.common.enums;

/**
 * @author hovanvydut
 * Created on 8/8/21
 */

public enum ArticleType {
    POST(0), SERIES(1);

    private int type;

    ArticleType(int type) {
        this.type = type;
    }

    public int get() {
        return this.type;
    }

    public ArticleType of(int type) {
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

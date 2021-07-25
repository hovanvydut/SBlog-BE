package hovanvydut.apiblog.common.enums;

/**
 * @author hovanvydut
 * Created on 7/24/21
 */

public enum ReplyTypeEnum {
    COMMENT(0), REPLY(1);

    private int type;

    ReplyTypeEnum(int type) {
        this.type = type;
    }

    public int get() {
        return this.type;
    }
}

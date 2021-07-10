package hovanvydut.apiblog.common.constant;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

public enum ArticleVoteEnum {
    UP(1),
    DOWN(-1),
    CANCEL(0);

    private int vote;

    ArticleVoteEnum(int vote) {
        this.vote = vote;
    }

    public int get() {
        return this.vote;
    }
}

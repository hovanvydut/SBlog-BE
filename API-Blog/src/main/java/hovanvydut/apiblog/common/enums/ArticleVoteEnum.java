package hovanvydut.apiblog.common.enums;

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

    public static ArticleVoteEnum of(int vote) {
        switch (vote) {
            case 1:
                return ArticleVoteEnum.UP;
            case -1:
                return ArticleVoteEnum.DOWN;
            case 0:
                return ArticleVoteEnum.CANCEL;
            default:
                // TODO: edit message error
                throw new RuntimeException("Not mapped value ArticleVoteEnum");
        }
    }
}

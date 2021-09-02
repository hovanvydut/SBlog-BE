package hovanvydut.apiblog.entity.enums;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

public enum ArticleVoteType {
    UP(1),
    DOWN(-1);
    private int vote;

    ArticleVoteType(int vote) {
        this.vote = vote;
    }

    public int get() {
        return this.vote;
    }

    public static ArticleVoteType of(int vote) {
        switch (vote) {
            case 1:
                return ArticleVoteType.UP;
            case -1:
                return ArticleVoteType.DOWN;
            default:
                // TODO: edit message error
                throw new RuntimeException("Not mapped value ArticleVoteEnum");
        }
    }
}

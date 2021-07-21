package hovanvydut.apiblog.api.v1.request;

import hovanvydut.apiblog.common.constant.PagingConstant;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class CommentArticlePaginationParams extends PaginationParams {

    public CommentArticlePaginationParams() {
        super();
        this.size = PagingConstant.COMMENT_PER_PAGE;
    }
}

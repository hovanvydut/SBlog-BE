package hovanvydut.apiblog.api.v1.comment.dto;

import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.common.request.PaginationParams;

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

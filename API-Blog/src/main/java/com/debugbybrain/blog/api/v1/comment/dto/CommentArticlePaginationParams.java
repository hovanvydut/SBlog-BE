package com.debugbybrain.blog.api.v1.comment.dto;

import com.debugbybrain.blog.common.constant.PagingConstant;
import com.debugbybrain.blog.common.request.PaginationParams;

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

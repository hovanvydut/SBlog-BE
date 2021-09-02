package com.debugbybrain.blog.api.v1.article.dto;

import com.debugbybrain.blog.common.constant.PagingConstant;
import com.debugbybrain.blog.common.request.PaginationParams;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class ArticlePaginationParams extends PaginationParams {

    public ArticlePaginationParams() {
        super();
        this.size = PagingConstant.ARTICLE_PER_PAGE;
    }

}
